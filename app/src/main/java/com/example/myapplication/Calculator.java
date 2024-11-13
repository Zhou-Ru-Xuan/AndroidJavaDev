package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Calculator extends AppCompatActivity {

    private TextView tv_result;
    // 第一个操作数
    private String firstNum = "";
    // 运算符
    private String operator = "";
    // 第二个操作数
    private String secondNum = "";
    // 当前的计算结果
    private String result = "";
    // 显示的文本内容
    private String showText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 从布局文件中获取名叫tv_result的文本视图
        tv_result = findViewById(R.id.tv_result);
        setButtonClickListeners();
    }

    private void setButtonClickListeners() {
        findViewById(R.id.btn_cancel).setOnClickListener(new CancelButtonClickListener());

        findViewById(R.id.btn_clear).setOnClickListener(new ClearButtonClickListener());

        findViewById(R.id.btn_divide).setOnClickListener(new CalOpButtonClickListener());
        findViewById(R.id.btn_multiply).setOnClickListener(new CalOpButtonClickListener());
        findViewById(R.id.btn_plus).setOnClickListener(new CalOpButtonClickListener());
        findViewById(R.id.btn_minus).setOnClickListener(new CalOpButtonClickListener());

        findViewById(R.id.btn_reciprocal).setOnClickListener(new ReciprocalButtonClickListener());

        findViewById(R.id.btn_equal).setOnClickListener(new EqualButtonClickListener());

        findViewById(R.id.ib_sqrt).setOnClickListener(new SqrtButtonClickListener());

        findViewById(R.id.btn_dot).setOnClickListener(new OtherButtonClickListener());

        findViewById(R.id.btn_zero).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_one).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_two).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_three).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_four).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_five).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_six).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_seven).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_eight).setOnClickListener(new OtherButtonClickListener());
        findViewById(R.id.btn_nine).setOnClickListener(new OtherButtonClickListener());
    }

    private String getInputText(View v) {
        if (v.getId() == R.id.ib_sqrt) {
            return "√";
        } else {
            return ((TextView) v).getText().toString();
        }
    }


    // 加减乘除四则运算，返回计算结果
    private double calculateFour() {
        switch (operator) {
            case "＋":
                return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
            case "－":
                return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
            case "×":
                return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
            default:
                return Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }
    }

    // 清空并初始化
    private void clear() {
        refreshOperate("");
        refreshText("");
    }

    // 刷新运算结果
    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }

    // 刷新文本显示
    private void refreshText(String text) {
        showText = text;
        tv_result.setText(showText);
    }

    private class CancelButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 处理取消按钮点击事件的逻辑
        }
    }

    /**
     * 加减乘除
     */
    private class CalOpButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            operator = getInputText(v); // 运算符
            refreshText(showText + operator);
        }
    }

    private class ClearButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            clear();
        }
    }

    private class OtherButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String inputText = getInputText(v);
            // 上次的运算结果已经出来了
            if (!result.isEmpty() && operator.isEmpty()) {
                clear();
            }

            // 无运算符，则继续拼接第一个操作数
            if (operator.isEmpty()) {
                firstNum = firstNum + inputText;
            } else {
                // 有运算符，则继续拼接第二个操作数
                secondNum = secondNum + inputText;
            }
            // 整数不需要前面的0
            if (showText.equals("0") && !inputText.equals(".")) {
                refreshText(inputText);
            } else {
                refreshText(showText + inputText);
            }
        }
    }

    private class ReciprocalButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            double reciprocal_result = 1.0 / Double.parseDouble(firstNum);
            refreshOperate(String.valueOf(reciprocal_result));
            refreshText(showText + "/=" + result);
        }
    }

    private class EqualButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            double calculate_result = calculateFour();
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "=" + result);
        }
    }

    private class SqrtButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            double sqrt_result = Math.sqrt(Double.parseDouble(firstNum));
            refreshOperate(String.valueOf(sqrt_result));
            refreshText(showText + "√=" + result);
        }
    }
}

