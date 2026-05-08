import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class QuizApplication extends JFrame implements ActionListener {

    JLabel questionLabel;
    JLabel timerLabel;
    JLabel qNumberLabel;

    JRadioButton opt1;
    JRadioButton opt2;
    JRadioButton opt3;
    JRadioButton opt4;

    JButton nextBtn;
    JButton prevBtn;

    ButtonGroup group;
    JProgressBar progressBar;

    int currentQuestion = 0;
    int score = 0;
    int timeLeft = 30;

    Timer timer;

    // QUESTIONS
    String[] questions = {

"1. Which package is used for Swing?",

"2. Which class creates a main window?",

"3. Which component displays text?",

"4. Which event is triggered on button click?"

};



String[][] options = {

{"java.swing", "javax.swing", "java.awt", "javax.awt"},

{"JFrame", "JLabel", "JPanel", "JButton"},

{"JButton", "JLabel", "JTextField", "JCheckBox"},

{"MouseEvent", "KeyEvent", "ActionEvent", "FocusEvent"}

};



int[] answers = {

1, // javax.swing

0, // JFrame

1, // JLabel

2  // ActionEvent

};

    public QuizApplication() {

        setTitle("Java Swing Quiz Application");

        // Question Number
        qNumberLabel = new JLabel();
        qNumberLabel.setBounds(50, 20, 200, 30);
        add(qNumberLabel);

        // Progress bar
        progressBar = new JProgressBar(0, questions.length);
        progressBar.setBounds(50, 5, 250, 10);
        add(progressBar);

        // Timer
        timerLabel = new JLabel("Time left: 30");
        timerLabel.setBounds(300, 10, 120, 30);
        add(timerLabel);

        // Question
        questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 400, 30);
        add(questionLabel);

        // Options
        opt1 = new JRadioButton();
        opt1.setBounds(50, 100, 300, 30);
        add(opt1);

        opt2 = new JRadioButton();
        opt2.setBounds(50, 130, 300, 30);
        add(opt2);

        opt3 = new JRadioButton();
        opt3.setBounds(50, 160, 300, 30);
        add(opt3);

        opt4 = new JRadioButton();
        opt4.setBounds(50, 190, 300, 30);
        add(opt4);

        // Group radio buttons
        group = new ButtonGroup();
        group.add(opt1);
        group.add(opt2);
        group.add(opt3);
        group.add(opt4);

        // Previous button
        prevBtn = new JButton("Previous");
        prevBtn.setBounds(40, 230, 100, 30);
        prevBtn.addActionListener(this);
        add(prevBtn);

        // Next button
        nextBtn = new JButton("Next");
        nextBtn.setBounds(160, 230, 100, 30);
        nextBtn.addActionListener(this);
        add(nextBtn);

        setSize(450, 350);

        setLayout((LayoutManager) null);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadQuestion();

        startTimer();
    }

    void loadQuestion() {

        qNumberLabel.setText(
                "Question "
                        + (currentQuestion + 1)
                        + " of "
                        + questions.length
        );

        progressBar.setValue(currentQuestion + 1);

        group.clearSelection();

        questionLabel.setText(questions[currentQuestion]);

        opt1.setText(options[currentQuestion][0]);
        opt2.setText(options[currentQuestion][1]);
        opt3.setText(options[currentQuestion][2]);
        opt4.setText(options[currentQuestion][3]);

        timeLeft = 30;
    }

    void startTimer() {

        timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                timeLeft--;

                timerLabel.setText("Time left: " + timeLeft);

                if (timeLeft <= 0) {

                    timer.stop();

                    nextQuestion();
                }
            }
        });

        timer.start();
    }

    void checkAnswer() {

        int selected = -1;

        if (opt1.isSelected())
            selected = 0;

        if (opt2.isSelected())
            selected = 1;

        if (opt3.isSelected())
            selected = 2;

        if (opt4.isSelected())
            selected = 3;

        if (selected == answers[currentQuestion])
            score++;
    }

    void nextQuestion() {

        checkAnswer();

        currentQuestion++;

        if (currentQuestion < questions.length) {

            loadQuestion();

            startTimer();
        }

        else {

            showResult();
        }
    }

    void showResult() {

        int percent = score * 100 / questions.length;

        String performance;

        if (percent >= 80)
            performance = "Excellent";

        else if (percent >= 60)
            performance = "Good";

        else
            performance = "Needs Improvement";

        JFrame resultFrame = new JFrame("Result");

        JLabel resultLabel = new JLabel(

                "<html>"
                        + "<h2>Quiz Result</h2>"
                        + "Total Questions: " + questions.length + "<br>"
                        + "Correct Answers: " + score + "<br>"
                        + "Wrong Answers: " + (questions.length - score) + "<br>"
                        + "Percentage: " + percent + "%<br>"
                        + "Performance: " + performance
                        + "</html>"
        );

        resultLabel.setBounds(40, 30, 300, 200);

        resultFrame.add(resultLabel);

        resultFrame.setSize(350, 250);

        resultFrame.setLayout(null);

        resultFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        timer.stop();

        if (e.getSource() == nextBtn)

            nextQuestion();

        if (e.getSource() == prevBtn) {

            if (currentQuestion > 0) {

                currentQuestion--;

                loadQuestion();

                startTimer();
            }
        }
    }

    public static void main(String[] args) {

        new QuizApplication();
    }
}