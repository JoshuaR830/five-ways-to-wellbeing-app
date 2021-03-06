package com.joshuarichardson.fivewaystowellbeing.storage;

import android.content.SharedPreferences;

import com.joshuarichardson.fivewaystowellbeing.ActivityType;
import com.joshuarichardson.fivewaystowellbeing.WaysToWellbeing;
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule;
import com.joshuarichardson.fivewaystowellbeing.storage.dao.WellbeingQuestionDao;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingQuestion;
import com.joshuarichardson.fivewaystowellbeing.surveys.SurveyItemTypes;

/**
 * A helper to get all the items that need to be inserted into the wellbeing question table
 */
public class DatabaseQuestionHelper {

    public static final int VERSION_NUMBER = 7;

    public static final int ACTIVITY_OF_TYPE_VALUE = 50;

    private static final int HIGH_VALUE = 20;
    private static final int MEDIUM_VALUE = 10;
    private static final int LOW_VALUE = 5;

    /**
     * Create a list of sub-activity questions.
     * These questions have suggested ways to improve and encouraging messages, along with a weighting for the sub-activity.
     *
     * @return A list of questions that facilitate sub-activities
     */
    public static WellbeingQuestion[] getQuestions() {
        return new WellbeingQuestion[] {

            // Add questions for app
            new WellbeingQuestion(1, "Did you interact with anyone through the app?", "You boosted your connect score by using an app with someone else!", "You could try to use the app together with someone else to form deeper connections", WaysToWellbeing.CONNECT.toString(), LOW_VALUE, ActivityType.APP.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(2, "Did you take regular breaks from your phone?", "You increased your be active score by taking breaks from your apps, great work!", "If possible, try taking regular breaks from your apps, be active, even if it's just walking to the window!", WaysToWellbeing.BE_ACTIVE.toString(), LOW_VALUE, ActivityType.APP.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(3, "Did you learn anything new?", "You learnt something new while using the app", "Try to learn something new when using apps", WaysToWellbeing.KEEP_LEARNING.toString(), HIGH_VALUE, ActivityType.APP.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(4, "Did the app give you a new perspective?", "The app helped you to explore new perspectives boosting your take notice score!", "How could you use apps that help you to reflect on yourself and your surroundings to boost your take notice score?", WaysToWellbeing.TAKE_NOTICE.toString(), MEDIUM_VALUE, ActivityType.APP.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(5, "Did you use this app to spread positivity?", "You gave by spreading positivity, nice one!", "Taking time to share positivity is a great way to give - it can be a boost to others and yourself", WaysToWellbeing.GIVE.toString(), MEDIUM_VALUE, ActivityType.APP.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Add questions for sport
            new WellbeingQuestion(6, "Did you exercise with another person?", "You improved your connect score by exercising with another person!", "You could try doing sport with another person it's a great way to connect!", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.SPORT.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(7, "Were you active for 10+ minutes?", "Woop woop, being active for more than 10 minutes is worthy of bonus points - nice one!", "If you have time, it'd be great to try to get longer bursts of physical activity into your day!", WaysToWellbeing.BE_ACTIVE.toString(), MEDIUM_VALUE, ActivityType.SPORT.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(8, "Did you develop your skills?", "Great job, growing in skills means you are learning new things!", "You could try learning new ways to do a sport you love, it can be very rewarding!", WaysToWellbeing.KEEP_LEARNING.toString(), MEDIUM_VALUE, ActivityType.SPORT.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(9, "Did you notice anything new in your surroundings?", "Spotted it - that's right, you took notice of your surroundings!", "You could try taking a different route, what do you notice?", WaysToWellbeing.TAKE_NOTICE.toString(), LOW_VALUE, ActivityType.SPORT.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(10, "Did you greet or encourage someone else?", "Great job! You boosted your give score by acknowledging a passer by!", "Even just a small nod can give someone a boost - why not give it a try next time you are out and about?", WaysToWellbeing.GIVE.toString(), LOW_VALUE, ActivityType.SPORT.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Add questions for hobby
            new WellbeingQuestion(11, "Did anyone else join in?", "You boosted your connect score by spending time doing your hobby with another person!", "Sharing your hobby with another person can be a great way to connect with them deeply.", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.HOBBY.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(12, "Were you physically active?", "Great job, you found a way to be active while doing your hobby!", "Is there a way you could be active while doing your hobby?", WaysToWellbeing.BE_ACTIVE.toString(), MEDIUM_VALUE, ActivityType.HOBBY.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(13, "Did you advance your skills?", "Amazing, you advanced your skills which helped you to keep learning!", "Rather than repeating the same things, have you thought about advancing your skills and learning new things as you do your hobby?", WaysToWellbeing.KEEP_LEARNING.toString(), HIGH_VALUE, ActivityType.HOBBY.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(14, "Did it reveal anything new about yourself?", "Nice one, by noticing something new about yourself your hobby helped you to take notice!", "Taking time to see yourself in new ways through your hobbies can really help you to take notice.", WaysToWellbeing.TAKE_NOTICE.toString(), MEDIUM_VALUE, ActivityType.HOBBY.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(15, "Did you lend a helping hand?", "Amazing, you gave by lending a helping hand while doing your hobby!", "You can give by sharing knowledge about your hobby with other people and lending a helping hand!", WaysToWellbeing.GIVE.toString(), HIGH_VALUE, ActivityType.HOBBY.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Add questions for pet
            new WellbeingQuestion(16, "Did you talk or interact with your pet?", "Well done, interacting with your pet will help you to stay connected!", "Try to interact more with your pet, that will really help you to build a sense of connection!", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.PET.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(17, "Did you play with your pet?", "You boosted your be active score by playing with your pet.", "Playing with your pet is a great way to be active, why not give it a try?", WaysToWellbeing.BE_ACTIVE.toString(), MEDIUM_VALUE, ActivityType.PET.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(18, "Did you learn a new fact about your pet?", "That's so cool, you learned something new about your pet!", "Go on, why not find out some facts about your pet, see if you learn something new!", WaysToWellbeing.KEEP_LEARNING.toString(), MEDIUM_VALUE, ActivityType.PET.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(19, "Did you notice a new behaviour?", "Woah, you took time to take notice and saw your pet do something new!", "Try taking notice of your pet, is there anything about them you haven't seen before?", WaysToWellbeing.TAKE_NOTICE.toString(), MEDIUM_VALUE, ActivityType.PET.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(20, "Did you spend quality time with your pet?", "Amazing, you boosted your give score by giving up time for your pet!", "You could give up more time for your pet, giving love and time will benefit you both! ", WaysToWellbeing.GIVE.toString(), HIGH_VALUE, ActivityType.PET.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Add questions for work
            new WellbeingQuestion(21, "Were you working with anyone else?", "Great job, connecting with other people at work is a great way to boost your wellbeing!", "If you work with other people, try to connect with them regularly!", WaysToWellbeing.CONNECT.toString(), MEDIUM_VALUE, ActivityType.WORK.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(22, "Did you walk over to a colleague instead of communicating digitally?", "Well done, you dropped the digital communication and boosted your be active score!", "Next time you need to talk to a colleague, could you be active, either by walking over to them, or by walking around while on a call?", WaysToWellbeing.BE_ACTIVE.toString(), LOW_VALUE, ActivityType.WORK.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(23, "Did you learn something new at work?", "Nice one, learning at work is a great way to boost your wellbeing!", "Try to incorporate learning new things into your work routine, it could help you to find more job satisfaction!", WaysToWellbeing.KEEP_LEARNING.toString(), HIGH_VALUE, ActivityType.WORK.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(24, "Has anything changed in your work environment?", "You boosted your take notice score by spotting new things in your surroundings at work!", "You could look for things that have changed in your work surroundings to boost your take notice score, regular screen breaks will be beneficial too.", WaysToWellbeing.TAKE_NOTICE.toString(), MEDIUM_VALUE, ActivityType.WORK.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(25, "Did you offer to do anything for someone e.g. make a drink?", "Good job, you gave by doing something nice for someone during your work day!", "You could try to incorporate giving into your working life, whether housemate, friend or colleague, next time you grab a beverage, why not see if they want one too?", WaysToWellbeing.GIVE.toString(), HIGH_VALUE, ActivityType.WORK.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Add questions for learning
            new WellbeingQuestion(26, "Was it a group activity?", "You boosted your connect score by learning with other people!", "Taking part in group learning can build connections with people, you could look for more opportunities to do this!", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.LEARNING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(27, "Did you get up and move around?", "You got your be active points by getting up and moving around while learning, great job!", "When you are learning, try to get up and move around regularly to be active", WaysToWellbeing.BE_ACTIVE.toString(), MEDIUM_VALUE, ActivityType.LEARNING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(28, "Did you gain new knowledge?", "You know it - gaining new knowledge is one of the perks of learning!", "To gain new knowledge, try to find things that you are interested in to learn about (you're more likely to remember it)", WaysToWellbeing.KEEP_LEARNING.toString(), HIGH_VALUE, ActivityType.LEARNING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(29, "Did you have a new revelation?", "They might be rare, but new revelations boost your take notice score - great work!", "By taking notice in your learning, you could be a penny drop away from a new revelation!", WaysToWellbeing.TAKE_NOTICE.toString(), HIGH_VALUE, ActivityType.LEARNING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(30, "Did you share knowledge with someone else?", "Spending time sharing what you've learned is a great way to give and you smashed it!", "Try to share what you learn, this is a great way to give to others and reinforce it for yourself!", WaysToWellbeing.GIVE.toString(), HIGH_VALUE, ActivityType.LEARNING.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Chores
            new WellbeingQuestion(31, "Was it a team effort?", "Great work, doing chores together makes everything better and it helps you to connect!", "Try working together to get the jobs done, it might just help you connect too!", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.CHORES.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(32, "Was physical exertion required?", "You were active while doing chores, great job!", "Chores don't have to be boring, try moving around and and you can get some be active points too!", WaysToWellbeing.BE_ACTIVE.toString(), HIGH_VALUE, ActivityType.CHORES.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(33, "Did you learn a new technique?", "You boosted your keep learning score by learning new ways of doing chores!", "Even chores you've been doing for years can be spiced up by learning a new way to do it!", WaysToWellbeing.KEEP_LEARNING.toString(), MEDIUM_VALUE, ActivityType.CHORES.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(34, "Did you do it in time to music?", "You took notice and did your chores in time with the music - what a pro!", "Doing chores in time to music can make them feel less of a chore and can boost your take notice score!", WaysToWellbeing.TAKE_NOTICE.toString(), MEDIUM_VALUE, ActivityType.CHORES.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(35, "Was it to help someone else?", "You boosted your give score by doing chores to help other people - great job!", "Giving your time to help other people makes chores so much more valuable - why not give it a go?", WaysToWellbeing.GIVE.toString(), HIGH_VALUE, ActivityType.CHORES.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Cooking
            new WellbeingQuestion(36, "Did you cook with someone else?", "You boosted your connect score by cooking with someone else - nicely done!", "If possible, try to get other people involved in cooking, even if it's from afar, it's a great way to connect!", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.COOKING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(37, "Did you have to grab lots of ingredients or utensils?", "Grabbing all those thing to cook with, you're basically an acrobat - keep cooking and keep being active!", "Why not get that energy and enthusiasm back - grab the things you need to cook like some kind of superhero for the ultimate be active cooking experience!", WaysToWellbeing.BE_ACTIVE.toString(), LOW_VALUE, ActivityType.COOKING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(38, "Did you try a new recipe or method?", "You spiced things up by learning a new recipe - amazing work!", "Why not embark upon a culinary quest - you could learn a new recipe!", WaysToWellbeing.KEEP_LEARNING.toString(), HIGH_VALUE, ActivityType.COOKING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(39, "Did you use a utensil you'd forgotten you had?", "Spotting that old utensil in the back of the cupboard - great work - you even earned yourself some take notice points!", "Have you ever been on an adventure to the back of your cupboards - go on - it's a great take notice opportunity - you never know what you might find!", WaysToWellbeing.TAKE_NOTICE.toString(), MEDIUM_VALUE, ActivityType.COOKING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(40, "Was it a shared meal?", "Making a meal for someone else is a wonderful way to give - you're a star!", "Making a meal to share with someone else is a great way to give, and it could give you opportunities to connect with them too - why not give it a try?", WaysToWellbeing.GIVE.toString(), HIGH_VALUE, ActivityType.COOKING.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Exercise
            new WellbeingQuestion(41, "Did you exercise with another person?", "You connected by exercising with another person - nicely done!", "Try talking to another person while exercising, even from afar - it's a great way to connect!", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.EXERCISE.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(42, "Were you active for 10+ minutes?", "Woop woop, being active for more than 10 minutes is worthy of bonus points - nice one!", "If you have time, it'd be great to try to get longer bursts of physical activity into your day!", WaysToWellbeing.BE_ACTIVE.toString(), MEDIUM_VALUE, ActivityType.EXERCISE.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(43, "Did you develop your skills?", "Great job, growing in skills means you are learning new things!", "You could try learning new ways to do a sport you love, it can be very rewarding!", WaysToWellbeing.KEEP_LEARNING.toString(), MEDIUM_VALUE, ActivityType.EXERCISE.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(44, "Did you notice anything new in your surroundings?", "Spotted it - that's right, you took notice of your surroundings while exercising!", "You could try taking a different route next time you exercise, what do you notice?", WaysToWellbeing.TAKE_NOTICE.toString(), LOW_VALUE, ActivityType.EXERCISE.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(45, "Did you greet or encourage someone else?", "Great job! You boosted your give score by acknowledging a passer by!", "Even just a small nod can give someone a boost - why not give it a try next time you are out and about?", WaysToWellbeing.GIVE.toString(), LOW_VALUE, ActivityType.EXERCISE.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Relaxation
            new WellbeingQuestion(46, "Did you relax with someone else?", "Great job, you relaxed with someone else helping you to connect!", "Taking time to chill with someone either in person or from afar can be a great way to connect - why not give it a go?", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.RELAXATION.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(47, "Did you move in a relaxed manner?", "You relaxed while moving - that's worthy of a be active boost - well done!", "Moving slowly can be relaxing and helps you to keep being active, no pressure - just calm!", WaysToWellbeing.BE_ACTIVE.toString(), MEDIUM_VALUE, ActivityType.RELAXATION.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(48, "Did it enhance your knowledge?", "You gained new knowledge while relaxing - that's got to be worth a keep learning boost!", "Relaxing doesn't mean you can't learn something new - what could you learn?", WaysToWellbeing.KEEP_LEARNING.toString(), MEDIUM_VALUE, ActivityType.RELAXATION.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(49, "Did you have a new experience?", "You took notice while relaxing today - great work!", "Meditative relaxation can help you to explore new things and really take notice, why not give it a try?", WaysToWellbeing.TAKE_NOTICE.toString(), MEDIUM_VALUE, ActivityType.RELAXATION.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(50, "Did you create a calming environment?", "You gave by creating a calming environment it didn't just benefit you, but the people around you too!", "Have you ever thought that taking time to create a calming environment could benefit the people around you too? What a beautiful way to give to yourself and others!", WaysToWellbeing.GIVE.toString(), LOW_VALUE, ActivityType.RELAXATION.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // People
            new WellbeingQuestion(51, "Did you have a long conversation or a deep chat?", "You had a great conversation, this is the essence of connect - good job!", "Going beyond a quick hello or how's the weather will really help you to get to know people - why not give it a try to discover the essence of connect!", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.PEOPLE.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(52, "Did you go out and about?", "You went out and about with someone which boosted your be active score!", "Going out and about with other people and exploring the great outdoors together is a great way to be active - why not give it a try?", WaysToWellbeing.BE_ACTIVE.toString(), MEDIUM_VALUE, ActivityType.PEOPLE.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(53, "Did you learn something new about them?", "You learned something new about someone - you kept learning - woop woop!", "Take time to learn new things about the people you are with, they might just surprise you!", WaysToWellbeing.KEEP_LEARNING.toString(), MEDIUM_VALUE, ActivityType.PEOPLE.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(54, "Did you notice anything different about them?", "You took notice by giving your full attention to someone and you noticed something new - amazing!", "To boost your take notice score you could pay real attention to the people you are with - is there anything different about them?", WaysToWellbeing.TAKE_NOTICE.toString(), MEDIUM_VALUE, ActivityType.PEOPLE.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(55, "Did you share knowledge or give them advice?", "You gave by sharing knowledge or advice with someone, great job!", "You could give by sharing knowledge or advice with someone!", WaysToWellbeing.GIVE.toString(), HIGH_VALUE, ActivityType.PEOPLE.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Journaling
            new WellbeingQuestion(56, "Did you talk to someone else about your day?", "You connected by talking to someone about your day while journaling!", "Talking about your day with someone while journaling can be a great way to connect with them!", WaysToWellbeing.CONNECT.toString(), MEDIUM_VALUE, ActivityType.JOURNALING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(57, "Did you wander around while considering what to write?", "Sometimes while journaling you just can't beat a good old pace up and down to get the ink flowing - you deserve a be active medal!", "Pacing around can really help you think - why not try it next time you journal - it could give you a be active boost too!", WaysToWellbeing.BE_ACTIVE.toString(), LOW_VALUE, ActivityType.JOURNALING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(58, "Did you learn anything about yourself as you journaled?", "You learned something new about yourself as you journaled - that's cool!", "Why not try to think about what you can learn from your experiences as you journal?", WaysToWellbeing.KEEP_LEARNING.toString(), MEDIUM_VALUE, ActivityType.JOURNALING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(59, "Did you consider anything in depth?", "You took notice by considering things in depth!", "Why not try to take notice of yourself and your surroundings as you journal?", WaysToWellbeing.TAKE_NOTICE.toString(), HIGH_VALUE, ActivityType.JOURNALING.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(60, "Did you take time to think about how you helped other people?", "Great job, you took time to journal about how you give to others", "Taking time to journal about how you helped others is a great way to reflect and improve your give score", WaysToWellbeing.GIVE.toString(), LOW_VALUE, ActivityType.JOURNALING.toString(), SurveyItemTypes.CHECKBOX.toString()),

            // Faith
            new WellbeingQuestion(61, "Did you spend time talking with someone?", "While practicing your faith you connected - nice one!", "Spending time with other people through meeting or in prayer is a great way to connect - why not give it a go?", WaysToWellbeing.CONNECT.toString(), HIGH_VALUE, ActivityType.FAITH.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(62, "Did you move around in prayer or worship?", "Nice one, you were active while practicing your faith!", "Worship or prayer can look different for different people - could being active help you to build a more personal connection?", WaysToWellbeing.BE_ACTIVE.toString(), LOW_VALUE, ActivityType.FAITH.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(63, "Did you learn anything new?", "Your faith provided opportunities to keep learning and growing - keep it up!", "What could you learn from your faith - could you apply it to your day?", WaysToWellbeing.KEEP_LEARNING.toString(), MEDIUM_VALUE, ActivityType.FAITH.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(64, "Did you have a faith-based experience or revelation?", "You took notice while practicing your faith through having a new experience - that's amazing!", "Spending time taking notice while practicing faith can lead to new experiences and it might change how you see things!", WaysToWellbeing.TAKE_NOTICE.toString(), HIGH_VALUE, ActivityType.FAITH.toString(), SurveyItemTypes.CHECKBOX.toString()),
            new WellbeingQuestion(65, "Were you led to do something for someone else as a result?", "Practicing your faith led you to give time or energy to serve someone else - nice one!", "Do you ever feel led to give your time to serve someone else as you practice your faith?", WaysToWellbeing.GIVE.toString(), LOW_VALUE, ActivityType.FAITH.toString(), SurveyItemTypes.CHECKBOX.toString()),
        };
    }

    public static void updateQuestions(SharedPreferences preferences, WellbeingQuestionDao questionDao) {
        SharedPreferences.Editor preferenceEditor = preferences.edit();

        // Put the questions in the database whenever the questions are updated
        int appQuestionVersion = preferences.getInt("added_question_version", 0);
            if (appQuestionVersion != DatabaseQuestionHelper.VERSION_NUMBER) {
            WellbeingDatabaseModule.databaseExecutor.execute(() -> {
                for (WellbeingQuestion question : DatabaseQuestionHelper.getQuestions()) {
                    questionDao.insert(question);
                    questionDao.updateQuestion(question.getId(), question.getQuestion(), question.getPositiveMessage(), question.getNegativeMessage());
                }
            });
            preferenceEditor.putInt("added_question_version", DatabaseQuestionHelper.VERSION_NUMBER);
            preferenceEditor.apply();
        }
    }
}
