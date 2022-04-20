package com.wkq.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord.createMime
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.nfc.Tag
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wkq.nfc.databinding.ActivityMainBinding

/**
 * NFC 拉起页面
 */
class NFCActivity : AppCompatActivity(), NfcAdapter.CreateNdefMessageCallback {

    //支持的标签类型
    private var nfcAdapter: NfcAdapter? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter==null){
            Toast.makeText(this, "该机型不支持NFC", Toast.LENGTH_LONG).show()
            finish()
        }
        // Register callback  *设置一个回调，使用Android Beam（TM）动态生成要发送的NDEF消息。
        nfcAdapter?.setNdefPushMessageCallback(this, this)
    }


    override fun onResume() {
        super.onResume()
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            processIntent(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter!!.disableReaderMode(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    /**
     * 处理Intent携带的数据
     */
    private fun processIntent(intent: Intent) {
        // 处理北京公交卡的数据
        var tag = intent.extras
        if (tag==null)return
        //  tag.keySet()  可以获取key的名字
        var content = NFCUtil.bytesToHex((tag!!.get("android.nfc.extra.TAG") as Tag).id)
        binding?.tvContent!!.text = content
        Toast.makeText(this, "获取北京地铁卡数据:" + content, Toast.LENGTH_LONG).show()
    }

    override fun createNdefMessage(event: NfcEvent?): NdefMessage {
        val text = "Beam me up, Android!\n\n" +
                "Beam Time: " + System.currentTimeMillis()
        return NdefMessage(
            arrayOf(
                createMime("application/vnd.com.example.android.beam", text.toByteArray())
            )
        )

    }

}