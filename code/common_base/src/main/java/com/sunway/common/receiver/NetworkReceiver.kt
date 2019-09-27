package com.sunway.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sunway.common.constants.BaseConstant
import com.sunway.common.event.NetworkChangeEvent
import com.sunway.common.utils.NetWorkUtil
import com.sunway.common.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 *  @author BuMingYang
 *  @des 网络监听
 */
class NetworkReceiver : BroadcastReceiver() {

    //缓存上一次的网络状态
    private var hasNetwork: Boolean by Preference(BaseConstant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }

}