package ch.approppo.androidtesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private TextView textView;

	private Integer stack;

	public enum Operation {
		SUM, SUBTRACT, MULTIPLY, DIVIDE
	}

	private Operation operation;

	private Calculator calculator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		calculator = new Calculator();
		findAllViews();
	}

	private void findAllViews() {
		textView = (TextView) findViewById(R.id.result);
		findViewById(R.id.zero).setOnClickListener(this);
		findViewById(R.id.one).setOnClickListener(this);
		findViewById(R.id.two).setOnClickListener(this);
		findViewById(R.id.sum).setOnClickListener(this);
		findViewById(R.id.subtract).setOnClickListener(this);
		findViewById(R.id.multiply).setOnClickListener(this);
		findViewById(R.id.divide).setOnClickListener(this);
		findViewById(R.id.equals).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.zero:
			textView.setText("0");
			break;
		case R.id.one:
			textView.setText("1");
			break;
		case R.id.two:
			textView.setText("2");
			break;
		case R.id.sum:
			stack = readNumber();
			operation = Operation.SUM;
			break;
		case R.id.subtract:
			stack = readNumber();
			operation = Operation.SUBTRACT;
			break;
		case R.id.multiply:
			stack = readNumber();
			operation = Operation.MULTIPLY;
			break;
		case R.id.divide:
			stack = readNumber();
			operation = Operation.DIVIDE;
			break;
		case R.id.equals:
			performOperation();
			break;
		}
	}

	public void performOperation() {
		Integer number = readNumber();
		if (stack == null || number == null || operation == null) {
			return;
		}
		switch (operation) {
		case SUM:
			textView.setText(String.valueOf(calculator.sum(stack,number)));
			break;
		case SUBTRACT:
			textView.setText(String.valueOf(calculator.subtract(stack,number)));
			break;
		case MULTIPLY:
			textView.setText(String.valueOf(calculator.multiply(stack,number)));
			break;
		case DIVIDE:
			textView.setText(String.valueOf(Math.round(calculator.divide(stack,number))));
			break;
		}
		stack = number;
	}

	public Integer getStack() {
		return stack;
	}

	public Operation getOperation() {
		return operation;
	}

	private Integer readNumber() {
		try {
			return Integer.parseInt(textView.getText().toString());
		}
		catch (NumberFormatException e) {
			return null;
		}
	}
}
