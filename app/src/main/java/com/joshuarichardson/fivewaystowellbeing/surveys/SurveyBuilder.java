package com.joshuarichardson.fivewaystowellbeing.surveys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.joshuarichardson.fivewaystowellbeing.R;

import java.util.ArrayList;
import java.util.List;

import static com.joshuarichardson.fivewaystowellbeing.surveys.SurveyItemTypes.BASIC_SURVEY;

public class SurveyBuilder {

    Context context;

    List<SurveyQuestion> questions;

    boolean hasBasicSurvey;
    private List<String> basicSurveyQuestions;

    public SurveyBuilder(Context context) {
        this.context = context;
        this.questions = new ArrayList<>();
    }

    public SurveyBuilder withQuestion(SurveyQuestion question) {
        this.questions.add(question);
        return this;
    }

    public SurveyBuilder withBasicSurvey(List<String> activities) {
        this.basicSurveyQuestions = activities;
        this.hasBasicSurvey = true;
        return this;
    }

    public LinearLayout build() {
        LinearLayout layout = new LinearLayout(this.context);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        int counter = 0;

        if(hasBasicSurvey) {
            LayoutInflater basicSurveyInflater = LayoutInflater.from(this.context);
            View basicSurvey = basicSurveyInflater.inflate(R.layout.basic_survey_details, layout, false);
            basicSurvey.setTag(BASIC_SURVEY);
            TextInputLayout dropDownContainer = basicSurvey.findViewById(R.id.survey_activity_input_container);
            AutoCompleteTextView dropDownInput = dropDownContainer.findViewById(R.id.survey_activity_input);
            List<String> myQuestions = this.basicSurveyQuestions;

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.context, R.layout.item_list_text, myQuestions);
            dropDownInput.setAdapter(adapter);
            layout.addView(basicSurvey);
        }

        for(SurveyQuestion question : this.questions) {
            switch (question.getQuestionType()) {
                case DROP_DOWN_LIST:
                    LayoutInflater inflater = LayoutInflater.from(this.context);
                    View cardView = inflater.inflate(R.layout.input_drop_down, layout, false);

                    cardView.setId(counter);
                    cardView.setTag(question.getQuestionType());

                    TextInputLayout container = cardView.findViewById(R.id.drop_down_container);
                    AutoCompleteTextView dropDownInput = container.findViewById(R.id.drop_down_input);
                    List<String> myQuestions = question.getOptionsList();

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context, R.layout.item_list_text, myQuestions);
                    dropDownInput.setAdapter(adapter);

                    TextView questionText = cardView.findViewById(R.id.question_title);
                    questionText.setText(question.getQuestionText());

                    container.setHint(question.getQuestionText());

                    layout.addView(cardView);

                    break;
                case TEXT:
                    LayoutInflater textInflater = LayoutInflater.from(this.context);
                    View textCard = textInflater.inflate(R.layout.input_text, layout, false);

                    textCard.setId(counter);
                    textCard.setTag(question.getQuestionType());

                    TextInputLayout textContainer = textCard.findViewById(R.id.text_input_container);
                    TextView title = textCard.findViewById(R.id.question_title);

                    title.setText(question.getQuestionText());

                    textContainer.setHint(question.getQuestionText());

                    layout.addView(textCard);
                default:
                    break;
            }
            counter ++;
        }

        return layout;
    }
}
