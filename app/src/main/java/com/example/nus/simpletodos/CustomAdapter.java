package com.example.nus.simpletodos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by nus on 10/10/16.
 */

public class CustomAdapter extends BaseAdapter {
    private RealmResults<Notes> notes;
    private Context context;

    private EditText content;
    private TextView date;
    private ImageButton edit_btn;
    private ImageButton delete_btn;
    private Realm realm;
    public CustomAdapter(RealmResults<Notes> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.note_item, null);


        content = (EditText) v.findViewById(R.id.content_EditText);
        date = (TextView) v.findViewById(R.id.date_TextView);
        edit_btn = (ImageButton) v.findViewById(R.id.edit_btn);
        delete_btn = (ImageButton) v.findViewById(R.id.delete_btn);

        realm.init(context);
        realm = Realm.getDefaultInstance();

        content.setText(notes.get(position).getContent());
        date.setText(notes.get(position).getDate().toString());
        edit_btn.setOnClickListener(new View.OnClickListener() {
            EditText text;
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                // Get the layout inflater
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                final View v2 = inflater.inflate(R.layout.new_note, null);
                text = (EditText) v2.findViewById(R.id.newNote_EditText);
                text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            InputMethodManager imm = (InputMethodManager) ((Activity)context).getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        }
                    }
                });
                text.setText(notes.get(position).getContent());
                text.requestFocus();
                builder.setView(v2)
                        // Add action buttons
                        .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //final EditText newNote = (EditText) v2.findViewById(R.id.newNote_EditText) ;
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Notes myNote = notes.get(position);
                                        myNote.setContent(text.getText().toString());
                                        text.clearFocus();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                text.clearFocus();
                            }
                        }).setTitle(R.string.editNote);
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        text.clearFocus();
                    }
                });
                builder.create();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.areYouSure)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Notes toDelete = notes.get(position);
                                        toDelete.deleteFromRealm();
                                        myCallBack = (CustomAdapter.Callback)((Activity)context);
                                        myCallBack.updateUI();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        return v;
    }
    Callback myCallBack;
    interface Callback {
        public void updateUI();
    }



}
