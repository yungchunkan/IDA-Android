package ida.org.hk.ida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import clas.ServerHandler;

public class RegisterActivity extends NavigationActivity {
    private TextInputLayout il_username, il_email, il_password;
    private EditText username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_register);

        il_username = (TextInputLayout) findViewById(R.id.il_username);
        il_email = (TextInputLayout) findViewById(R.id.il_email);
        il_password = (TextInputLayout) findViewById(R.id.il_password);

        username = (EditText) findViewById(R.id.register_username);
        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);

        username.addTextChangedListener(new RegisterTextWatcher(username));
        email.addTextChangedListener(new RegisterTextWatcher(email));
        password.addTextChangedListener(new RegisterTextWatcher(password));
    }

    private boolean validateName() {
        if (username.getText().toString().trim().isEmpty()) {
            il_username.setError(getString(R.string.err_msg_username));
            requestFocus(username);
            return false;
        } else {
            il_username.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        String email_str = email.getText().toString().trim();

        if (email_str.isEmpty() || !isValidEmail(email_str)) {
            il_email.setError(getString(R.string.err_msg_email));
            requestFocus(email);
            return false;
        } else {
            il_email.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            il_password.setError(getString(R.string.err_msg_password));
            requestFocus(password);
            return false;
        } else {
            il_password.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void register(View view) {

        new RegisterTask().execute(email.getText().toString(), password.getText().toString());
    }

    private class RegisterTextWatcher implements TextWatcher {
        private View view;

        public RegisterTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.register_username:
                    validateName();
                    break;
                case R.id.register_email:
                    validateEmail();
                    break;
                case R.id.register_password:
                    validatePassword();
            }
        }
    }

    private class RegisterTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            ServerHandler handler = new ServerHandler();
            handler.addUser(params[0], params[1]);
            return true;
        }
    }
}
