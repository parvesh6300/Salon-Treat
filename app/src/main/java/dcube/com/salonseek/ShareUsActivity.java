package dcube.com.salonseek;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

public class ShareUsActivity extends Activity {

    TextView done;

    ImageView facebook, twiter, insta, email;

    SocialAuthAdapter adapter;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_us);

        done = (TextView) findViewById(R.id.done);

        facebook = (ImageView) findViewById(R.id.facebook);
        twiter = (ImageView) findViewById(R.id.twiter);
        insta = (ImageView) findViewById(R.id.insta);
        email = (ImageView) findViewById(R.id.email);

        shareDialog = new ShareDialog(this);

        // Add it to Library
        adapter = new SocialAuthAdapter(new ResponseListener());

        adapter.addProvider(SocialAuthAdapter.Provider.FACEBOOK, R.drawable.facebook);
        adapter.addProvider(SocialAuthAdapter.Provider.TWITTER, R.drawable.twitter);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                    i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"dcuberohit1@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Review About Salon App");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");

                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ShareUsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        facebook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.e("Facebook", "Facebook share clicked");
                try {
                    adapter.addConfig(SocialAuthAdapter.Provider.FACEBOOK,"138557703280102","4cd3310f26a5f1e1971bd86492d99d16","publish_actions");
                } catch (Exception e) {
                    e.printStackTrace();
                }

              //  adapter.authorize(ShareUsActivity.this, SocialAuthAdapter.Provider.FACEBOOK);
                //   adapter.updateStatus("Sharing Status", new MessageListener(), false);

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Welcome to Salon")
                            .setImageUrl(Uri.parse("https://www.numetriclabz.com/wp-content/uploads/2015/11/114.png"))
                            .setContentDescription("Review Of Salon App")
                            .setContentUrl(Uri.parse("https://www.numetriclabz.com/android-linkedin-integration-login-tutorial/"))
                            .build();
                    shareDialog.show(linkContent);  // Show facebook ShareDialog
                }

            }
        });

        twiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Twitter", "Twitter share clicked");

//                adapter.authorize(ShareUsActivity.this, SocialAuthAdapter.Provider.TWITTER);

            }
        });


        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Instagram", "Insta share clicked");

//                adapter.authorize(ShareUsActivity.this, SocialAuthAdapter.Provider.INSTAGRAM);

            }
        });


    }


    private final class ResponseListener implements DialogListener {
        @Override
        public void onComplete(Bundle values) {

            adapter.updateStatus("5:16 PM...", new MessageListener(), true);

        }

        @Override
        public void onError(SocialAuthError error) {
            Log.e("ShareButton", "Authentication Error: " + error.getMessage());
        }

        @Override
        public void onCancel() {
            Log.e("ShareButton", "Authentication Cancelled");
        }

        @Override
        public void onBack() {
            Log.e("Share-Button", "Dialog Closed by pressing Back Key");
        }

    }

    // To get status of message after authentication
    private final class MessageListener implements SocialAuthListener<Integer> {
        @Override
        public void onExecute(String provider, Integer t) {

            Integer status = t;

            if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
            {
                Toast.makeText(ShareUsActivity.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
            }

            else
                Toast.makeText(ShareUsActivity.this, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SocialAuthError e) {

            Log.e("Error", "Social Auth Error " + e.getLocalizedMessage());

        }
    }


}
