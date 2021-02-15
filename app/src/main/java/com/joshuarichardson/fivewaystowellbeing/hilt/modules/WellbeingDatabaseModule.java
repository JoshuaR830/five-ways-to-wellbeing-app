package com.joshuarichardson.fivewaystowellbeing.hilt.modules;

import android.content.Context;
import android.util.Log;

import com.joshuarichardson.fivewaystowellbeing.WaysToWellbeing;
import com.joshuarichardson.fivewaystowellbeing.storage.DatabaseQuestionHelper;
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.QuestionsToAsk;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyQuestionSet;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingQuestion;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_DATABASE_NAME;
import static com.joshuarichardson.fivewaystowellbeing.surveys.SurveyItemTypes.BASIC_SURVEY;

@Module
@InstallIn(ApplicationComponent.class)
public class WellbeingDatabaseModule {
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    // Add the new wellbeing column to the activity_records table
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE activity_records ADD COLUMN way_to_wellbeing TEXT DEFAULT " + WaysToWellbeing.UNASSIGNED.toString());
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            // Create the new questions table
            database.execSQL("CREATE TABLE wellbeing_questions (" +
                "wellbeing_question_id INTEGER NOT NULL PRIMARY KEY, " +
                "question TEXT NOT NULL, " +
                "positive_message TEXT NOT NULL, " +
                "negative_message TEXT NOT NULL, " +
                "way_to_wellbeing TEXT NOT NULL, " +
                "weighting INTEGER NOT NULL, " +
                "activity_type TEXT NOT NULL, " +
                "input_type TEXT NOT NULL " +
                ")"
            );

            // Create the new wellbeing records table
            database.execSQL("CREATE TABLE wellbeing_records (" +
                "wellbeing_record_id INTEGER NOT NULL PRIMARY KEY, " +
                    "time INTEGER NOT NULL, " +
                    "user_input INTEGER NOT NULL default 0, " +
                    "survey_response_activity_record_id INTEGER NOT NULL, " +
                    "sequence_number INTEGER NOT NULL default 0, " +
                    "question_id INTEGER NOT NULL, "  +
                    "FOREIGN KEY(question_id) REFERENCES wellbeing_questions(wellbeing_question_id) ON DELETE CASCADE, " +
                    "FOREIGN KEY(survey_response_activity_record_id) REFERENCES survey_activity(survey_activity_id) ON DELETE CASCADE" +
                    ")"
            );

            // Create a new table that will replace the survey_activity table - this is required because old table has the wrong primary key
            // Reference: medium.com/@pekwerike/handling-roomdb-migration-create-a-new-primary-key-column-in-an-existing-entity-f15d10932f5b
            database.execSQL("CREATE TABLE new_survey_activity (" +
                    "survey_activity_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "survey_response_id INTEGER NOT NULL, " +
                    "activity_record_id INTEGER NOT NULL, " +
                    "sequence_number INTEGER NOT NULL, " +
                    "note TEXT, " +
                    "start_time INTEGER NOT NULL, " +
                    "end_time INTEGER NOT NULL, " +
                    "FOREIGN KEY(activity_record_id) REFERENCES activity_records(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY(survey_response_id) REFERENCES survey_response(id) ON DELETE CASCADE" +
                ");"
            );

            // Insert the values from the old table and new default values into the new table
            database.execSQL("INSERT INTO new_survey_activity(" +
                    "survey_response_id, " +
                    "activity_record_id, " +
                    "sequence_number, " +
                    "note, " +
                    "start_time, " +
                    "end_time " +
                ")" +
                "SELECT survey_response_id, activity_record_id, 0, null, -1, -1 FROM survey_activity"
            );

            // Delete the old table
            database.execSQL("DROP TABLE survey_activity");

            // Rename the new table
            database.execSQL("ALTER TABLE new_survey_activity RENAME TO survey_activity");
        }
    };

    @Provides
    @Singleton
    public static WellbeingDatabase getWellbeingDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, WellbeingDatabase.class, WELLBEING_DATABASE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .addCallback(new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        Date now = new Date();
                        long setId = getWellbeingDatabase(context).surveyQuestionSetDao().insert(new SurveyQuestionSet(now.getTime(), 0));
                        getWellbeingDatabase(context).questionsToAskDao().insert(new QuestionsToAsk("", "", setId, BASIC_SURVEY.toString(), 0, null));

                        for (WellbeingQuestion question : DatabaseQuestionHelper.getQuestions()) {
                            getWellbeingDatabase(context).wellbeingQuestionDao().insert(question);
                        }
                    });
                }

                @Override
                public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                    super.onDestructiveMigration(db);
                    databaseWriteExecutor.execute(() -> {
                        Date now = new Date();
                        long setId = getWellbeingDatabase(context).surveyQuestionSetDao().insert(new SurveyQuestionSet(now.getTime(), 0));
                        getWellbeingDatabase(context).questionsToAskDao().insert(new QuestionsToAsk("", "", setId, BASIC_SURVEY.toString(), 0, null));

                        for (WellbeingQuestion question : DatabaseQuestionHelper.getQuestions()) {
                            getWellbeingDatabase(context).wellbeingQuestionDao().insert(question);
                        }
                    });
                }
            })
            .fallbackToDestructiveMigration()
            .build();
        }
}
