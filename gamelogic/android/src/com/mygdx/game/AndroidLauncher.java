package com.mygdx.game;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class AndroidLauncher extends AndroidApplication implements AndroidInterfaces{
	final AndroidLauncher context=this;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGdxGame(context), config);
	}

	@Override
	public void toast(final boolean answer) {
		handler.post(new Runnable()
		{
			@Override
			public void run() {
				Toast toast = new Toast(context);
				ImageView view = new ImageView(context);
				if (answer){
				view.setImageResource(R.drawable.true_answer);}
				else {
					view.setImageResource(R.drawable.false_answer);
				}
				toast.setView(view);
				toast.show();
			}

		});
	}
}
