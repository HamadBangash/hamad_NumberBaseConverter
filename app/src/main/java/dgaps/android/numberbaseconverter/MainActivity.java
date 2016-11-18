package dgaps.android.numberbaseconverter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvBinary, tvOctal, tvDecimal, tvHex, tv1, tv2, tv3, tv4;
    Button btnCal;
    ImageButton btnReset;
    RadioButton btnBinary, btnOctal, btnDecimal, btnHex;
    EditText etInput;
    RadioGroup rdGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvBinary = (TextView) findViewById(R.id.tvBinary);
        tvOctal = (TextView) findViewById(R.id.tvOctal);
        tvDecimal = (TextView) findViewById(R.id.tvDecimal);
        tvHex = (TextView) findViewById(R.id.tvHex);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);


        btnCal = (Button) findViewById(R.id.btnCal);
        btnReset = (ImageButton) findViewById(R.id.btnReset);

        btnBinary = (RadioButton) findViewById(R.id.btnBinary);
        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);

        etInput = (EditText) findViewById(R.id.etInput);

        tvBinary.setVisibility(View.INVISIBLE);
        tvOctal.setVisibility(View.INVISIBLE);
        tvDecimal.setVisibility(View.INVISIBLE);
        tvHex.setVisibility(View.INVISIBLE);

        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        tv3.setVisibility(View.INVISIBLE);
        tv4.setVisibility(View.INVISIBLE);

        calculation();
        changingHintAndType();
        refreshData();

    }

    private void refreshData() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etInput.setText("");

                tvBinary.setText("NaN");
                tvOctal.setText("NaN");
                tvDecimal.setText("NaN");
                tvHex.setText("NaN");

                btnBinary.setChecked(true);
                Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void calculation() {

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String radioValue = ((RadioButton) findViewById(rdGroup.getCheckedRadioButtonId())).getText().toString();
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                tvBinary.setText("NaN");
                tvOctal.setText("NaN");
                tvDecimal.setText("NaN");
                tvHex.setText("NaN");

                tvBinary.setVisibility(View.VISIBLE);
                tvOctal.setVisibility(View.VISIBLE);
                tvDecimal.setVisibility(View.VISIBLE);
                tvHex.setVisibility(View.VISIBLE);

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);

                String userInput = etInput.getText().toString();
                if (etInput.getText().length() == 0) {

                    Toast.makeText(MainActivity.this, "Enter Value", Toast.LENGTH_SHORT).show();
                } else {
                    if (radioValue.equals("Binary")) {
                        boolean flag = isBinary(userInput);
                        if (!flag) {
                            BigInteger convertedInput = new BigInteger(userInput);
                            long toDecimal = Long.parseLong(String.valueOf(convertedInput), 2);
                            String octalResult = Long.toOctalString(toDecimal);
                            String hexDecimalResult = Long.toHexString(toDecimal);
                            tvBinary.setText(userInput);
                            tvOctal.setText(octalResult);
                            tvDecimal.setText(toDecimal + "");
                            tvHex.setText(hexDecimalResult);
                        } else {
                            Toast.makeText(MainActivity.this, "Sorry entered value is not a binary!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (radioValue.equals("Octal")) {
                        boolean flag = isOctal(userInput);
                        if (flag) {
                            BigInteger convertedInput = new BigInteger(userInput);
                            long toDecimal = Long.parseLong(String.valueOf(convertedInput), 8);
                            String binaryResult = Long.toBinaryString(toDecimal);
                            String hexDecimalResult = Long.toHexString(toDecimal);
                            tvBinary.setText(binaryResult);
                            tvOctal.setText(userInput);
                            tvDecimal.setText(toDecimal + "");
                            tvHex.setText(hexDecimalResult);
                        } else {
                            Toast.makeText(MainActivity.this, "Sorry entered value is not an octal!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (radioValue.equals("Decimal")) {
                        BigInteger convertedInput = new BigInteger(userInput);
                        long toDecimal = Long.parseLong(String.valueOf(convertedInput));
                        String binaryResult = Long.toBinaryString(toDecimal);
                        String octalResult = Long.toOctalString(toDecimal);
                        String hexDecimalResult = Long.toHexString(toDecimal);
                        tvBinary.setText(String.valueOf(binaryResult));
                        tvOctal.setText(String.valueOf(octalResult));
                        tvDecimal.setText(userInput);
                        tvHex.setText(String.valueOf(hexDecimalResult));
                    } else if (radioValue.equals("Hex-Dec")) {

                        boolean flag = isHexDecimal(userInput);
                        if (flag) {
//                            BigInteger convertedInput = new BigInteger(userInput);
                            long toDecimal = Long.parseLong(String.valueOf(userInput), 16);
                            String binaryResult = Long.toBinaryString(toDecimal);
                            String octalResult = Long.toOctalString(toDecimal);
                            String hexDecimalResult = Long.toHexString(toDecimal);
                            tvBinary.setText(binaryResult);
                            tvOctal.setText(octalResult);
                            tvDecimal.setText(toDecimal + "");
                            tvHex.setText(userInput);
                        } else {
                            Toast.makeText(MainActivity.this, "Sorry entered value is not a hex-decimal!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }
        });
    }


    private void changingHintAndType() {
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String radioValue = ((RadioButton) findViewById(rdGroup.getCheckedRadioButtonId())).getText().toString();

                if (radioValue.equals("Binary")) {
                    etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etInput.setHint("Enter Binary Value");
                    etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
                    etInput.setText("");

                    tvBinary.setText("NaN");
                    tvOctal.setText("NaN");
                    tvDecimal.setText("NaN");
                    tvHex.setText("NaN");

                } else if (radioValue.equals("Octal")) {
                    etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etInput.setHint("Enter Octal Value");
                    etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(21)});
                    etInput.setText("");

                    tvBinary.setText("NaN");
                    tvOctal.setText("NaN");
                    tvDecimal.setText("NaN");
                    tvHex.setText("NaN");

                } else if (radioValue.equals("Decimal")) {
                    etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etInput.setHint("Enter Decimal Value");
                    etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
                    etInput.setText("");

                    tvBinary.setText("NaN");
                    tvOctal.setText("NaN");
                    tvDecimal.setText("NaN");
                    tvHex.setText("NaN");

                } else if (radioValue.equals("Hex-Dec")) {
                    etInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    etInput.setHint("Enter Hex-Decimal Value");
                    etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                    etInput.setText("");

                    tvBinary.setText("NaN");
                    tvOctal.setText("NaN");
                    tvDecimal.setText("NaN");
                    tvHex.setText("NaN");
                }


            }
        });

    }


    private static boolean isBinary(String number) {
        boolean bad = false;
        for (char c : number.toCharArray())
            if (!(c == '0' || c == '1')) {
                bad = true;
                break;
            }

        return bad;
    }

    private static boolean isOctal(String number) {
        return number.matches("[0-7]*$");
    }

    private static boolean isHexDecimal(String number) {
        return number.matches("^[0-9a-fA-F]+$");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about_us) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_dialog, null);
            final AlertDialog customDialog = new AlertDialog.Builder(this).create();
            customDialog.setView(view);
            customDialog.setTitle("About Us");
            ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
            ivImage.setImageResource(R.drawable.logo);
            TextView tvMsg = (TextView) view.findViewById(R.id.tvMsg);
            tvMsg.setText("We Are Digital Applications");
            customDialog.show();
            customDialog.setCanceledOnTouchOutside(true);
        }
        if (id == R.id.our_apps) {

        }
        return super.onOptionsItemSelected(item);
    }
}
