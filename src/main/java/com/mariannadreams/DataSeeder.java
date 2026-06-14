package com.mariannadreams;

import com.mariannadreams.model.Lyric;
import com.mariannadreams.model.Lyric.SectionType;
import com.mariannadreams.model.Song;
import com.mariannadreams.model.SiteContent;
import com.mariannadreams.repository.LyricRepository;
import com.mariannadreams.repository.SongRepository;
import com.mariannadreams.service.SiteContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Seeds the SQLite database with the full Marianna Dreams self-titled debut album.
 *
 * <p>Album: Marianna Dreams (Self-Titled Debut)
 * Spotify: https://open.spotify.com/album/0BB8BawGzPa6yNdyf9vGBb
 *
 * <p>Guard check (songRepository.count() == 0) makes re-runs safe.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final SongRepository songRepository;
    private final LyricRepository lyricRepository;
    private final SiteContentService siteContentService;

    private static final String ALBUM_SPOTIFY = "https://open.spotify.com/album/0BB8BawGzPa6yNdyf9vGBb";
    private static final String GENRE = "Roots · Folk · Country · Indie";
    private static final int YEAR = 2024;

    @Override
    public void run(String... args) {
        seedSiteContent();
        if (songRepository.count() > 0) {
            log.info("DataSeeder: database already seeded, skipping.");
            return;
        }
        log.info("DataSeeder: seeding Marianna Dreams debut album (13 tracks)...");

        seedTrack(1,  "Roots and Wings",
                "https://open.spotify.com/track/2Vge8kJ7fKVsgjtu3PHpVj",
                "https://open.spotify.com/embed/track/2Vge8kJ7fKVsgjtu3PHpVj?utm_source=oembed",
                "Where you come from and where you're going — sometimes the roots are the wings.",
                true);

        seedTrack(2,  "Leaving Marianna",
                "https://open.spotify.com/track/6iuGWZytfTqKAVKVBlzfIj",
                "https://open.spotify.com/embed/track/6iuGWZytfTqKAVKVBlzfIj?utm_source=oembed",
                "A bittersweet farewell to a small town that never quite lets you go.",
                true);

        seedTrack(3,  "Down the Juke Joint Line",
                "https://open.spotify.com/track/3GySTzf94pzmV1S5ebaGNP",
                "https://open.spotify.com/embed/track/3GySTzf94pzmV1S5ebaGNP?utm_source=oembed",
                "Neon signs, sawdust floors, and a fiddle that makes the whole room move.",
                true);

        seedTrack(4,  "Wildflower Mile",
                "https://open.spotify.com/track/2iSKFVmFjV51Rqpmm4oW8f",
                "https://open.spotify.com/embed/track/2iSKFVmFjV51Rqpmm4oW8f?utm_source=oembed",
                "That stretch of road where the Queen Anne's lace and black-eyed Susans own the shoulders.",
                true);

        // Track 5 — the flagship single with full lyrics
        Song honeysuckle = seedTrack(5, "Honeysuckle Summer Breeze",
                "https://open.spotify.com/track/4LvdAmtQev8e3n9pSkXvlu",
                "https://open.spotify.com/embed/track/4LvdAmtQev8e3n9pSkXvlu?utm_source=oembed",
                "Windows down on a backroad county line, air thick as molasses in the Carolina pines.",
                true);
        seedHoneysuckleLyrics(honeysuckle);

        // Track 6
        seedTrack(6,  "I Wanna be Present (with you)",
                "https://open.spotify.com/track/5W1zfkbegTDp94AhHof42m",
                "https://open.spotify.com/embed/track/5W1zfkbegTDp94AhHof42m?utm_source=oembed",
                "A love song for the age of distraction — put the phone down and just be here.",
                true);

        seedTrack(7,  "Friday Night Forever",
                "https://open.spotify.com/track/0szYhZb5JknADWf0SDPIu4",
                "https://open.spotify.com/embed/track/0szYhZb5JknADWf0SDPIu4?utm_source=oembed",
                "Stadium lights, gravel lots, and the kind of night you swear you'll never forget.",
                true);

        seedTrack(8,  "Small Town Saints",
                "https://open.spotify.com/track/3oVIwsE0s9IJtyM3keTLf2",
                "https://open.spotify.com/embed/track/3oVIwsE0s9IJtyM3keTLf2?utm_source=oembed",
                "The quietly heroic lives lived in small towns that most people drive right through.",
                true);

        seedTrack(9,  "Faded Blue Jeans",
                "https://open.spotify.com/track/4hGULOdTSTBXacx9gHdEB2",
                "https://open.spotify.com/embed/track/4hGULOdTSTBXacx9gHdEB2?utm_source=oembed",
                "Worn soft by a hundred summer days — some things only get better with time.",
                true);

        seedTrack(10, "Enough",
                "https://open.spotify.com/track/5H6jc2yr58p1AJGv2RyFlt",
                "https://open.spotify.com/embed/track/5H6jc2yr58p1AJGv2RyFlt?utm_source=oembed",
                "A quiet anthem for anyone who's ever wondered if they measure up.",
                true);

        seedTrack(11, "Ghosts in the Rearview",
                "https://open.spotify.com/track/0DpgXDUfG0Cvr5cqesYcPC",
                "https://open.spotify.com/embed/track/0DpgXDUfG0Cvr5cqesYcPC?utm_source=oembed",
                "The past keeps pace with you no matter how fast you drive.",
                true);

        seedTrack(12, "Where the River Bends",
                "https://open.spotify.com/track/5tNhHoY4pzhi4amInYAwJ0",
                "https://open.spotify.com/embed/track/5tNhHoY4pzhi4amInYAwJ0?utm_source=oembed",
                "At the curve in the river where the herons stand still and time seems to agree with them.",
                true);

        seedTrack(13, "Porch Light Left On",
                "https://open.spotify.com/track/7gzjoNgZtPcBunZsY44AjX",
                "https://open.spotify.com/embed/track/7gzjoNgZtPcBunZsY44AjX?utm_source=oembed",
                "Home is the porch light that stays on no matter how long you've been gone.",
                true);

        log.info("DataSeeder: all 13 tracks seeded successfully.");
    }

    // ── Helper: create and save a Song entity ────────────────────────────────
    private Song seedTrack(int trackNum, String title, String spotifyUrl, String embedUrl,
                           String description, boolean featured) {
        Song song = new Song();
        song.setTitle(title);
        song.setSpotifyUrl(spotifyUrl);
        song.setEmbedUrl(embedUrl);
        song.setImageUrl(null); // Angular falls back to album_art.png asset
        song.setGenre(GENRE);
        song.setReleaseYear(YEAR);
        song.setAiToolsUsed("Suno");
        song.setFeaturedStatus(featured);
        song.setDisplayOrder(trackNum);
        song.setDescription(description);
        return songRepository.save(song);
    }

    // ── Full lyrics for Track 5 — Honeysuckle Summer Breeze ─────────────────
    private void seedHoneysuckleLyrics(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order,
            "Windows down on a backroad county line,\n" +
            "Air thick as molasses in the Carolina pines.\n" +
            "Honeysuckle vines climbing the old fence post,\n" +
            "Sweet perfume rolling like a heavenly ghost.\n\n" +
            "Cut grass warm from the afternoon sun,\n" +
            "Magnolia heavy where the river runs.\n" +
            "Fireflies sparking as the daylight dies,\n" +
            "Breathin' in summer till it fills our eyes.");

        order = addLyric(song, "Pre-Chorus", SectionType.PRE_CHORUS, order,
            "No perfume counter, no city smoke and steel,\n" +
            "Just the earth and the sky making something real.");

        order = addLyric(song, "Chorus", SectionType.CHORUS, order,
            "Honeysuckle summer breeze, carry me away,\n" +
            "Wrap me up in every golden day.\n" +
            "Sweet tea on the porch and the jasmine in her hair,\n" +
            "Thunder rolling distant, rain fresh in the air.\n\n" +
            "Yeah, we're livin' on the scent of wild blackberry wine,\n" +
            "Pine needles warm and that kudzu vine.\n" +
            "Let it lift us high, let it set our spirits free,\n" +
            "In this honeysuckle summer breeze.");

        order = addLyric(song, "Verse 2", SectionType.VERSE, order,
            "Pull over where the creek meets the gravel road,\n" +
            "Dirt road dust mixin' with the wild rose.\n" +
            "Night-blooming flowers open under moonlight glow,\n" +
            "Crickets singin' soft and the radio low.\n\n" +
            "Fresh rain on hot pavement, that ozone kiss,\n" +
            "Watermelon sugar drippin' down our wrists.\n" +
            "She leans in close with that sun-warmed skin,\n" +
            "Every breath a memory we're livin' in.");

        order = addLyric(song, "Pre-Chorus", SectionType.PRE_CHORUS, order,
            "No deadlines, no deadlines, just the moment and the air,\n" +
            "Nature's own perfume everywhere.");

        order = addLyric(song, "Chorus", SectionType.CHORUS, order,
            "Honeysuckle summer breeze, carry me away,\n" +
            "Wrap me up in every golden day.\n" +
            "Sweet tea on the porch and the jasmine in her hair,\n" +
            "Thunder rolling distant, rain fresh in the air.\n\n" +
            "Yeah, we're livin' on the scent of wild blackberry wine,\n" +
            "Pine needles warm and that kudzu vine.\n" +
            "Let it lift us high, let it set our spirits free,\n" +
            "In this honeysuckle summer breeze.");

        order = addLyric(song, "Bridge", SectionType.BRIDGE, order,
            "Let it roll through the open windows of this old Ford,\n" +
            "Let it tangle in our shirts like a soft reward.\n" +
            "From the Smokies down to the Gulf Coast shore,\n" +
            "Summer's sweet breath callin' for more…\n\n" +
            "More laughter, more stars, more barefoot nights,\n" +
            "More of this feeling that feels so right.");

        order = addLyric(song, "Final Chorus", SectionType.CHORUS, order,
            "Honeysuckle summer breeze, never let me go,\n" +
            "Paint these memories in every note we know.\n" +
            "From the juke joint porch to the long highway curve,\n" +
            "You're the reason that we're livin' to serve.\n\n" +
            "Yeah, we're burnin' slow on this great American ride,\n" +
            "With the scent of forever right by our side.\n" +
            "Mariana Dreams in the warm twilight glow,\n" +
            "Honeysuckle summer breeze… take us home.");

        addLyric(song, "Outro", SectionType.OUTRO, order,
            "Honeysuckle… summer breeze…\n" +
            "Carry me… carry me home…");
    }

    // ── Helper: create and save a Lyric block ────────────────────────────────
    private int addLyric(Song song, String label, SectionType type, int order, String content) {
        Lyric lyric = new Lyric();
        lyric.setSong(song);
        lyric.setSectionLabel(label);
        lyric.setSectionType(type);
        lyric.setContent(content);
        lyric.setDisplayOrder(order);
        lyricRepository.save(lyric);
        return order + 1;
    }

    /** Seed default editable page content (runs every startup, skips existing keys) */
    private void seedSiteContent() {
        siteContentService.seedIfEmpty(List.of(
            // ── Hero ──────────────────────────────────────────────────────
            new SiteContent("hero_eyebrow",   "Eyebrow text",    "Hero",       "An AI Band from the American South"),
            new SiteContent("hero_tagline",   "Tagline",         "Hero",       "Roots. Folk. Country. Indie."),
            new SiteContent("hero_sub",       "Sub-headline",    "Hero",       "Where honeysuckle meets heartstring, and every dirt road leads somewhere worth singing about."),
            // ── About Preview (home page) ─────────────────────────────────
            new SiteContent("about_title",    "Section title",   "About",      "Born from Red Clay Roads & Summer Dreams"),
            new SiteContent("about_text_1",   "Paragraph 1",     "About",      "Marianna Dreams is an AI-crafted musical project steeped in the sights, sounds, and soul of the American South. We write songs that smell like cut grass and pine needles, that taste like sweet tea and wild blackberry wine, that sound like the world slowing down just enough to breathe."),
            new SiteContent("about_text_2",   "Paragraph 2",     "About",      "Drawing from the deep wells of roots music, folk storytelling, country tradition, and indie spirit, Marianna Dreams creates sonic landscapes where every note carries the weight of a summer memory and the lightness of a firefly blinking at dusk."),
            new SiteContent("about_quote",    "Pull quote",      "About",      "Livin' on the scent of wild blackberry wine, pine needles warm and that kudzu vine."),
            new SiteContent("about_quote_src","Quote source",    "About",      "Honeysuckle Summer Breeze"),
            // ── Music Section (home page) ─────────────────────────────────
            new SiteContent("music_desc",     "Section description", "Music",  "Songs born from the scent of honeysuckle and the sound of summer."),
            // ── Newsletter ────────────────────────────────────────────────
            new SiteContent("nl_desc",        "Newsletter description", "Newsletter", "No spam. Just songs and stories from the South. 🌿")
        ));
        log.info("DataSeeder: site content seeded.");
    }
}
