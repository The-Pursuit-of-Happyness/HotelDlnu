package com.hotel.itemactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hotel.hoteldlnu.R;

public class AddMessDialogFragment extends DialogFragment {
	private EditText messname;
	private EditText messprice;
	private EditText messdescribe;
	private EditText messfeature;
	private EditText messeffect;


	public interface InputListener
	{
		void InputComplete(String messname, String messprice,String messdescribe,
				String messfeature,String messeffect);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_addmess, null);
		messname = (EditText) view.findViewById(R.id.et_messname);
		messprice = (EditText) view.findViewById(R.id.et_messprice);
		messdescribe = (EditText) view.findViewById(R.id.et_messdescribe);
		messfeature = (EditText) view.findViewById(R.id.et_messfeature);
		messeffect = (EditText) view.findViewById(R.id.et_messeffect);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(view)
				// Add action buttons
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int id)
							{
								InputListener listener = (InputListener) getActivity();
								listener.InputComplete(messname
										.getText().toString(), messprice
										.getText().toString(),messdescribe.getText().toString(),
										messfeature.getText().toString(),messeffect.getText().toString());
							}
						}).setNegativeButton("Cancel", null);
		return builder.create();
	}
}
