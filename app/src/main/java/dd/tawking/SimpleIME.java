package dd.tawking;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.List;

import dd.tawking.R;

public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard qkeyboard;
    private Keyboard nkeyboard;
    private Keyboard ekeyboard;

    private boolean caps = false;

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
       // playClick(primaryCode);
        switch (primaryCode) {

            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                qkeyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:

                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
           case Keyboard.KEYCODE_ALT: //case to switch from main keyboard to numbers

               break;

            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        qkeyboard = new Keyboard(this, R.xml.qwerty);
        nkeyboard = new Keyboard(this, R.xml.numbers);
        ekeyboard = new Keyboard(this, R.xml.emotes);
        kv.setKeyboard(qkeyboard);
        kv.setOnKeyboardActionListener(this);
        kv.setPreviewEnabled(false);
        return kv;
    }


    public View SetQKeyboard() {
        qkeyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(qkeyboard);
        kv.setOnKeyboardActionListener(this);
        kv.invalidateAllKeys();
        return kv;
    }


    public View SetNKeyboard() {
        nkeyboard = new Keyboard(this, R.xml.numbers);
        kv.setKeyboard(nkeyboard);
        kv.setOnKeyboardActionListener(this);
        kv.invalidateAllKeys();
        return kv;
    }

    public void TestKeys(){
        InputConnection ic = getCurrentInputConnection();
        for(int i=1; i<50; i++) {
            char code = (char)i;
            String inc = String.valueOf(i);
            ic.commitText(inc ,1);
            ic.commitText(String.valueOf(code), 1);
        }
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

        for(int i=51; i<100; i++) {
            char code = (char)i;
            String inc = String.valueOf(i);
            ic.commitText(inc ,1);
            ic.commitText(String.valueOf(code), 1);
        }
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

        for(int i=101; i<150; i++) {
            char code = (char)i;
            String inc = String.valueOf(i);
            ic.commitText(inc ,1);
            ic.commitText(String.valueOf(code), 1);
        }
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
    }

    private void playClick(int keyCode) {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }



}