package com.example.shazly.piazyapp.Notifications;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.widget.Toast;

import com.example.shazly.piazyapp.Activity.LoginActivity;
import com.example.shazly.piazyapp.Activity.PostActivity;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by shazly on 08/02/18.
 */

public class RetreiveFeedTask extends AsyncTask<String, Void, String> {

    Session session = null;
    String subject;
    String textMessage;
    String rec;
Context context;

    public RetreiveFeedTask(Session session, String subject, String textMessage, String rec, Context context) {
        this.session = session;
        this.subject = subject;
        this.textMessage = textMessage;
        this.rec = rec;
        this.context = context;
    }



    @Override
    protected String doInBackground(String... params) {

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("piazymanger@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(rec));
            message.setSubject(subject);
            message.setContent(textMessage, "text/html; charset=utf-8");
            Transport.send(message);
            Toast.makeText(context, "SendMessage",
                    Toast.LENGTH_SHORT).show();
        } catch(MessagingException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}