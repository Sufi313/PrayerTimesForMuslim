package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

//import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import java.util.List;
import java.util.Locale;

import io.reactivex.annotations.NonNull;


public class SurasNames {
    public static String[] jozaNamesAr = new String[]{"الأول", "الثاني", "الثالث", "الرابع", "الخامس", "السادس", "السابع", "الثامن", "التاسع", "العاشر", "الحادي عشر", "الثاني عشر", "الثالث عشر", "الرابع عشر", "الخامس عشر", "السادس عشر", "السابع عشر", "الثامن عشر", "التاسع عشر", "العشرون", "الأول والعشرون", "الثاني والعشرون", "الثالث والعشرون", "الرابع والعشرون", "الخامس والعشرون", "السادس والعشرون", "السابع والعشرون", "الثامن والعشرون", "التاسع والعشرون", "الثلاثون"};
    @NonNull
    public static int[] suraKinds = new int[]{0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
    @NonNull
    public static String[] suraNamesAr = new String[]{"الفاتحة", "البقرة", "آل عمران", "النساء", "المائدة", "الأنعام", "الأعراف", "الأنفال", "التوبة", "يونس", "هود", "يوسف", "الرعد", "إبراهيم", "الحجر", "النحل", "الإسراء", "الكهف", "مريم", "طه", "الأنبياء", "الحج", "المؤمنون", "النور", "الفرقان", "الشعراء", "النمل", "القصص", "العنكبوت", "الروم", "لقمان", "السجدة", "الأحزاب", "سبأ", "فاطر", "يس", "الصافات", "ص", "الزمر", "غافر", "فصلت", "الشورى", "الزخرف", "الدخان", "الجاثية", "الأحقاف", "محمد", "الفتح", "الحجرات", "ق", "الذاريات", "الطور", "النجم", "القمر", "الرحمن", "الواقعة", "الحديد", "المجادلة", "الحشر", "الممتحنة", "الصف", "الجمعة", "المنافقون", "التغابن", "الطلاق", "التحريم", "الملك", "القلم", "الحاقة", "المعارج", "نوح", "الجن", "المزمل", "المدثر", "القيامة", "الإنسان", "المرسلات", "النبأ", "النازعات", "عبس", "التكوير", "الانفطار", "المطففين", "الانشقاق", "البروج", "الطارق", "الأعلى", "الغاشية", "الفجر", "البلد", "الشمس", "الليل", "الضحى", "الشرح", "التين", "العلق", "القدر", "البينة", "الزلزلة", "العاديات", "القارعة", "التكاثر", "العصر", "الهمزة", "الفيل", "قريش", "الماعون", "الكوثر", "الكافرون", "النصر", "المسد", "الإخلاص", "الفلق", "الناس"};
    @NonNull
    public static String[] suraNamesEn = new String[]{"AlFatihah", "AlBaqarah ", "Al'Imran", "AnNisa'", "AlMa'idah", "AlAn'am", "AlA'raf", "AlAnfal", "AtTaubah", "Yunus", "Hud", "Yusuf", "ArRa'd", "Ibrahim", "AlHijr", "AnNahl", "AlIsra'", "AlKahf", "Maryam", "TaHa", "AlAnbiya'", "AlHajj", "AlMu'minun", "AnNur", "AlFurqan", "AshShu'ara'", "AnNaml", "AlQasas", "AlAnkabut", "ArRum", "Luqman", "AsSajdah", "AlAhzab", "Saba'", "Fatir", "YaSin", "AsSaffat", "Sad", "AzZumar", "Ghafir", "Fussilat", "AshShura", "AzZukhruf", "AdDukhan", "AlJathiyah", "AlAhqaf", "Muhammad", "AlFath", "AlHujurat", "Qaf", "AdhDhariyat", "AtTur", "AnNajm", "Qamar", "ArRahman", "AlWaqi'ah", "AlHadid", "AlMujadilah", "AlHashr", "AlMumtahanah", "AsSaff", "AlJumu'ah", "AlMunafiqun", "AtTaghabun", "AtTalaq", "AtTahrim", "AlMulk", "AlQalam", "AlHaqqah", "AlMa'arij", "Nuh", "AlJinn", "AlMuzzammil", "AlMuddaththir", "AlQiyamah", "AlInsan", "AlMursalat", "AnNaba'", "AnNazi'at", "Abasa", "AtTakwir", "AlInfitar", "AlMutaffifin", "AlInshiqaq", "AlBuruj", "AtTariq", "AlA'la", "AlGhashiyah", "AlFajr", "AlBalad", "AshShams", "AlLail", "AdDuha", "AshSharh", "AtTin", "AlAlaq", "AlQadr", "AlBayyinah", "AzZalzalah", "AlAadiyat", "AlQari'ah", "AtTakathur", "AlAsr", "AlHumazah", "AlFil", "Quraish", "AlMa'un", "AlKauthar", "AlKafirun", "AnNasr", "AlMasad", "AlIkhlas", "AlFalaq", "AnNas"};
    @NonNull
    public static int[] surasAyahs = new int[]{7, 286, 200, 176, 120, 165, 206, 75, 129, 109, 123, 111, 43, 52, 99, 128, 111, 110, 98, 135, 112, 78, 118, 64, 77, 227, 93, 88, 69, 60, 34, 30, 73, 54, 45, 83, 182, 88, 75, 85, 54, 53, 89, 59, 37, 35, 38, 29, 18, 45, 60, 49, 62, 55, 78, 96, 29, 22, 24, 13, 14, 11, 11, 18, 12, 12, 30, 52, 52, 44, 28, 28, 20, 56, 40, 31, 50, 40, 46, 42, 29, 19, 36, 25, 22, 17, 19, 26, 30, 20, 15, 21, 11, 8, 8, 19, 5, 8, 8, 11, 11, 8, 3, 9, 5, 4, 7, 3, 6, 3, 5, 4, 5, 6};
    List<String> suraNames;

    public String getSuraNames(int i) {
        if (Locale.getDefault().getLanguage().equals("ar")) {
            return suraNamesAr[i];
        }
        return suraNamesEn[i];
    }

    @NonNull
    public String[] getSuraNames() {
        if (Locale.getDefault().getLanguage().equals("ar")) {
            return suraNamesAr;
        }
        return suraNamesEn;
    }
}
