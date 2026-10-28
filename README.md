[Download client launcher pack](https://github.com/peecer/VoiceChat-Client-Side/raw/refs/heads/main/downloads/VoiceChat-Client-1.21.11-Fabric-0.18.2.mrpack)

# Voice Chat — Client

Simple proximity voice chat plus named, password-protected groups that work across the world. This repository provides an original **Peecer Voice TCP** transport add-on and packages **Simple Voice Chat by Max Henkel** for its microphone setup, proximity audio and group interface. Both endpoints must have the TCP add-on. The voice engine remains upstream Simple Voice Chat.

## Fabric 26.x client builds

The `fabric-26.x` folder includes separate setup instructions and official Simple Voice Chat 2.6.26 downloads for Minecraft **26.1, 26.2, and 26.3**. These builds use Fabric Loader 0.20.1+ and Java 25+. The same Peecer TCP client add-on is included for all three versions and for the existing 1.21.11 client. Open each version folder and follow its README.

## Exact client target

- Minecraft **1.21.11**
- Fabric Loader **0.18.2** — deliberately pinned, not upgraded
- Java **21** or newer compatible runtime
- Simple Voice Chat **2.6.26** (latest matching build checked October 27, 2026; upstream marks it beta)

[Download the required Simple Voice Chat client JAR](https://cdn.modrinth.com/data/9eGKb6K1/versions/hBnJeESK/voicechat-fabric-1.21.11-2.6.26.jar).

**Also required:** [Download the client TCP add-on](https://github.com/peecer/VoiceChat-Client-Side/raw/refs/heads/main/addons/peecer-voice-tcp-client-1.0.0.jar). Put BOTH JARs in `mods`.

## Install

1. Install Minecraft 1.21.11 with Fabric Loader 0.18.2 in your launcher.
2. Download both JARs above into that instance's `mods` folder. Remove previous Simple Voice Chat versions. Do not install the 26.3 or 26.4 snapshot JAR in this client, and do not install Plasmo Voice alongside this setup.
3. This voice mod bundles its required Fabric API modules. A separate Fabric API JAR is not required for this client-only setup. If other mods need the full Fabric API, use a compatible 1.21.11 version, at least 0.139.4+1.21.11.
4. Join the configured server, press **V**, and complete the microphone/speaker setup. Choose push-to-talk for a simple starting setup.

Alternatively download this repository using **Code → Download ZIP**, extract it, and run `install-windows.bat` (Windows) or `sh install-unix.sh` (macOS/Linux). Python 3.9+ is required. Copy `installed/mods` into your game instance. Advanced usage: `python install.py --output "/path/to/game-instance"` with the game closed.

For launcher import, the GitHub **Actions → Verify and package → latest successful run → voicechat-package** artifact contains a `.mrpack` that automatically selects Minecraft 1.21.11 and Loader 0.18.2 in a compatible launcher. It includes the TCP add-on and downloads the official voice engine JAR. You can also generate it with `python build_pack.py`.

## Create a private group

1. Press **V** and open **Groups / Join or Create Group**.
2. Choose **Create Group**, enter a name and optional password.
3. Choose **Isolated** for private group-only audio. Group members can speak across any distance and dimensions while connected to the same server.
4. Friends open the same group menu, choose the group, and enter its password.
5. Leave the group to return to proximity voice chat.

Group types: **Normal** means outsiders cannot hear you but you can hear nearby outsiders; **Open** lets nearby outsiders hear you too; **Isolated** prevents hearing or transmitting to outsiders. Voice groups do not connect separate Minecraft servers. Do not assume a group persists across server restarts.

## Server pairing

Use [VoiceChat-Server-Side](https://github.com/peecer/VoiceChat-Server-Side). It now targets Leaf/Paper Minecraft 26.3, matching the supplied server log, and uses the Bukkit versions of ViaVersion/ViaBackwards to accept this older client. Install Peecer Voice TCP Paper 1.0.1 and Simple Voice Chat Bukkit on the server; this client add-on 1.0.0 stays unchanged. This is intended for vanilla-style servers; arbitrary mods adding new blocks/items are not made compatible by this bridge.

## Troubleshooting

- A crossed-out plug means the voice connection is not established. With both TCP add-ons installed, the server must allow its separate **TCP** voice port (25791 for this host). Opening only the Minecraft game port is insufficient. No UDP port is needed.
- An operator can run `/voicechat test PLAYERNAME`.
- Check the selected mic, push-to-talk binding and operating-system microphone permission.
- If group creation is unavailable, verify `enable_groups=true` on the server.

## Validation and credits

The TCP add-on was compiled against the real 1.21.11 voice API. Loopback tests passed for TCP-only binding, byte framing, two-client routing/isolation, invalid packet rejection, close/unblock and reconnect. Downloaded JAR size, SHA-512, ZIP integrity, mod ID and version were checked. The client JAR requires Loader >=0.18.1, so the requested 0.18.2 satisfies its declared requirement. Installer tests cover damaged downloads and exact version pins. This add-on uses TCP only: it will not connect to an ordinary UDP-only Simple Voice Chat server. Temporarily remove the TCP add-on to use ordinary voice servers. A real Minecraft 26.3 server boot passed: all six server mods loaded, the custom TCP socket was selected, TCP 24454 opened, and shutdown exited with code 0. A graphical 1.21.11 client launch, cross-version player login and two-player microphone/audio tests have **not** been performed here.

The upstream JAR is downloaded unchanged. Upstream code/native libraries retain their own licenses; this repository does not grant permission to fork or relicense them.

- [Simple Voice Chat source](https://github.com/henkelmax/simple-voice-chat)
- [Client setup](https://dev.modrepo.de/minecraft/voicechat/wiki/client_setup)
- [Compatibility](https://dev.modrepo.de/minecraft/voicechat/wiki/compatibility)

## TCP transport details

This is an experimental 1.0.0 add-on. TCP retransmission can cause voice stutter or delays during packet loss, unlike UDP. A separate TCP socket is used so audio does not block the Minecraft connection. There is no UDP fallback. Both sides must run the same TCP add-on wire protocol.

The transport uses a versioned greeting, 4-byte frame lengths, a 4095-byte maximum frame, bounded receive/send queues, and 15-second read timeouts. It preserves the original voice payload unchanged; upstream voice authentication and encryption still apply. The server caps connections at 128 total and 8 per source IP, with 200 incoming packets/second per connection. Large groups behind one NAT may hit the per-IP cap. This is resource limiting, not a complete DDoS defense. Other custom voice-socket replacement add-ons are incompatible.

Build from source with **JDK 25 and Python 3.9+**: `python build.py` downloads the pinned voice API JAR, compiles original Java sources to Java 21 bytecode, runs TCP integration tests, and writes the add-on under `addons/`. The client needs Java 21; the Minecraft 26.3 server needs Java 25. `python build_pack.py` creates the launcher/server installation package. GitHub Actions repeats the build and tests.
