# NovaClient — Panduan Build

Source code lengkap NovaClient (mod Forge 1.8.9) ada di folder `src/`.
Karena compile mod Minecraft butuh download library dari internet, kamu
perlu build jar-nya sendiri di komputermu (sekali aja, ±5-10 menit).

## Yang kamu butuhkan
- **Java Development Kit 8** (JDK 8, bukan versi lebih baru — Forge 1.8.9 nggak jalan di JDK modern). Download: Adoptium Temurin 8.
- Koneksi internet (buat download Minecraft + Forge library saat build pertama).

## Cara build (Windows)
1. Extract folder `NovaClient` ini di mana saja.
2. Buka Command Prompt / PowerShell di dalam folder itu.
3. Jalankan:
   ```
   gradlew setupDecompWorkspace
   gradlew build
   ```
4. Kalau sukses, file jar final ada di:
   ```
   build/libs/NovaClient-1.0.0.jar
   ```
5. Drag file itu ke folder `mods` di instalasi **Minecraft Forge 1.8.9** kamu
   (`%appdata%/.minecraft/mods`). Jangan lupa install Forge 1.8.9 dulu kalau
   belum ada (installer resmi dari situs Forge).
6. Buka Minecraft pakai profile Forge 1.8.9, masuk ke game/world, lalu pencet
   **Right Shift** → menu NovaClient bakal muncul.

(Mac/Linux: pakai `./gradlew` bukan `gradlew`)

## Build otomatis lewat GitHub (nggak perlu install apa-apa di laptop)
Sudah disediakan `.github/workflows/build.yml` dan `.github/workflows/release.yml`.

1. Push folder ini ke repo GitHub kamu (public atau private sama-sama bisa).
2. Buka tab **Actions** di repo → workflow **"Build NovaClient"** akan otomatis
   jalan setiap kamu push ke branch `main`/`master` (atau klik **Run workflow**
   buat jalanin manual).
3. Setelah selesai (biasanya 3-8 menit), buka run yang barusan → scroll ke
   bagian **Artifacts** → download **NovaClient-jar** (berupa .zip berisi jar-nya).
4. Extract, lalu drag jar-nya ke folder `mods` seperti biasa.

Kalau mau versi yang otomatis nempel ke GitHub Release (jadi ada link download
permanen), tinggal kasih tag versi:
```
git tag v1.0.0
git push origin v1.0.0
```
Workflow **"Release NovaClient"** bakal build lalu bikin Release baru dengan
jar-nya ter-attach otomatis.

## Kalau workflow di Actions gagal (failed)
GitHub kadang cuma nampilin ekor stack trace-nya di layar utama, bukan pesan
error aslinya. Buat lihat penyebab sebenarnya:
1. Buka run yang gagal di tab **Actions**.
2. Klik step yang merah (`Setup ForgeGradle CI workspace` atau `Build mod jar`).
3. Di pojok kanan atas log ada ikon gerigi/roda → pilih **"View raw logs"**,
   atau scroll ke atas cari baris yang mulai dengan `FAILURE:` / `Caused by:`
   / `Exception`. Itu baris yang paling penting — copy-paste ke aku kalau
   masih gagal biar bisa aku diagnosa lebih tepat.

## Kalau gradlew nggak ada / error dependency
Project mod 1.8.9 yang lama kadang kena masalah karena beberapa server Maven
yang dipakai dulu (jcenter) udah nggak aktif. Kalau `gradlew build` di atas
error soal dependency yang nggak ketemu, cara paling gampang:

1. Clone salah satu template yang masih aktif dirawat, contoh:
   - https://github.com/isXander/Forge-1.8.9-Template
   - https://github.com/nea89o/Forge1.8.9Template
2. Copy semua isi folder `src/main/java/com/nova/client` dan
   `src/main/resources/mcmod.info` dari NovaClient ini ke dalam struktur
   `src` template tersebut (ganti/isi bagian source-nya).
3. Ganti `modid`/nama package di `build.gradle` template sesuai punya kamu
   kalau perlu, lalu jalankan build sesuai instruksi template itu.
4. Hasil jar-nya tetap sama: tinggal drag ke folder `mods`.

## Struktur kode (biar gampang dikembangin)
- `NovaClient.java` — entry point mod, daftarin keybind Right Shift & custom main menu.
- `handler/KeyInputHandler.java` — buka ClickGUI pas Right Shift dipencet.
- `handler/GuiHandler.java` — ganti main menu vanilla jadi `GuiNovaMainMenu`.
- `gui/ClickGUI.java` — menu modul: tab kategori di kiri, list modul di kanan
  dalam satu kotak di tengah layar. Ada tombol "Edit HUD" di pojok kanan atas.
- `gui/GuiHudEditor.java` — layar drag-and-drop buat naro posisi tiap elemen HUD.
- `gui/GuiNovaMainMenu.java` — main menu custom tema biru-putih.
- `module/Module.java` — kelas dasar tiap fitur/modul biasa.
- `module/HudModule.java` — kelas dasar khusus elemen HUD yang bisa di-drag.
- `module/ModuleManager.java` — daftar semua modul, panggil update/render.
- `module/impl/` — semua modul (lihat daftar di bawah).

## Daftar modul yang sudah ada
**Combat**
- KillAura — otomatis menyerang entity terdekat dalam jangkauan.
- Damage Indicator — kilatan merah di tepi layar saat kena damage.

**Movement**
- Speed, Fly, NoFall, Step, Jesus (jalan di atas air), AutoSprint.

**Render**
- Fullbright, Zoom, **X-Ray** (highlight ore lewat tembok, scan tiap ~1 detik),
  Entity ESP (highlight player/mob lewat tembok, merah = player, hijau = mob).

**HUD (bisa di-drag lewat "Edit HUD")**
- Keystrokes, Coordinates, FPS Display, Armor HUD, Potion Effects, Client Info.

**Misc**
- AntiAFK — geser pandangan dikit tiap beberapa detik biar nggak ke-kick AFK.

## Cara pakai menu & drag HUD
1. Pencet **Right Shift** in-game → muncul kotak menu di tengah layar.
2. Klik nama kategori di kolom kiri (Combat/Movement/Render/HUD/Misc).
3. Klik nama modul di kanan buat toggle ON/OFF.
4. Klik tombol **"Edit HUD"** di pojok kanan atas kotak menu → semua elemen
   HUD muncul sebagai kotak yang bisa di-drag, drag ke posisi yang kamu mau,
   lalu pencet Esc/Right Shift buat selesai. Posisi tersimpan selama sesi
   game berjalan (reset lagi kalau restart game — tinggal bilang kalau mau
   ditambahin supaya posisinya kesimpen permanen ke file config).

## Nambah modul baru
1. Buat class baru di `module/impl/`, extends `Module` (atau `HudModule`
   kalau elemen HUD yang mau bisa di-drag).
2. Override `onEnable()`, `onDisable()`, `onUpdate()`, dan/atau `onRender()`
   sesuai kebutuhan fiturnya.
3. Daftarin instance-nya di constructor `ModuleManager.java`.
4. Rebuild (`gradlew build`) — otomatis muncul di ClickGUI sesuai kategorinya.

## Catatan penting
Beberapa modul (KillAura, Fly, X-Ray, Entity ESP, Speed) itu jelas-jelas
cheat/unfair advantage — biasa kena anti-cheat atau melanggar aturan di
server survival publik yang serius. Aman-aman aja buat singleplayer atau
server privat/creative milik sendiri, tapi risiko ke-ban ditanggung sendiri
kalau dipakai di server orang lain.
