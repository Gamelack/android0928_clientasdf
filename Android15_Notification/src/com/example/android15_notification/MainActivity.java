package com.example.android15_notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
/**
 * 广播与通知没有必然联系
 * 
 * @author pzp
 *
 */
public class MainActivity extends Activity implements OnClickListener{
	Button send,clear;
	//通知管理器-用来发送通知
	NotificationManager manager;
	//通知-对象
	Notification notification;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		send=(Button)findViewById(R.id.send);
		clear=(Button)findViewById(R.id.clear);
		send.setOnClickListener(this);
		clear.setOnClickListener(this);
		//实例化一个通知
		init();
	}
	private void init() {
		notification=new Notification();
		//初始化通知
		notification.tickerText="有通知来了，亲";
		notification.icon=R.drawable.ic_launcher;
		notification.when=System.currentTimeMillis()-1000*60*5;//5分钟之前的通知
		//闪光
		notification.flags=Notification.DEFAULT_LIGHTS;
		notification.ledARGB=0xff003355;//#ff003355
		notification.ledOffMS=300;
		notification.ledOnMS=500;
		//设置声音
		notification.defaults|=Notification.DEFAULT_SOUND;
		//从数据库找默认的声音给通知
		notification.sound=Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "1");
		//其他设置
		notification.flags|=Notification.FLAG_AUTO_CANCEL;//点击后自动消失FLAG_NO_CLEAR//点击后不自动消失
		notification.flags|=Notification.FLAG_NO_CLEAR;//滑动不删除
		//BITMAP
		notification.largeIcon=BitmapFactory.decodeResource(getResources(), R.drawable.chrysanthemum);
	}
	//发送通知
	public void sendNotification(){
		if(manager==null){
			//获得通知管理器
			manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		}
		/*意图*/
		Intent intent=new Intent(this,OtherActivity.class);
		PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.contentIntent=pendingIntent;//意图
		/*????布局-RemoteViews--Linearlayout/Relaytivilayout...--不能使用复杂控件-ListView-SurfaceView...*/
		RemoteViews contentView=new RemoteViews(getPackageName(), R.layout.li);
		contentView.setTextViewText(R.id.title, "新婚50周年，发红包1000泰铢"+Math.random()*10);
		contentView.setTextViewText(R.id.content, "请拨打15926421805");
		contentView.setImageViewResource(R.id.iamge, R.drawable.chrysanthemum);
		
		notification.contentView=contentView;
		manager.notify("一号通知", 0, notification);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send:
			//发送通知
			sendNotification();
			break;
		case R.id.clear:
			//清除
			manager.cancel("一号通知",0);
			break;

		default:
			break;
		}
	}

	

}
