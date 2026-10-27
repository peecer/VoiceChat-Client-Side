package peecer.voice.tcp;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.ClientVoicechatInitializationEvent;
import de.maxhenkel.voicechat.api.events.EventRegistration;
public final class ClientPlugin implements VoicechatPlugin {
    public String getPluginId() { return "peecer_voice_tcp_client"; }
    public void registerEvents(EventRegistration events) {
        events.registerEvent(ClientVoicechatInitializationEvent.class,
                event -> event.setSocketImplementation(new TcpTransport.Client()));
    }
}
