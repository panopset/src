package com.panopset.web;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.google.gson.reflect.TypeToken;
import com.panopset.compat.AppVersion;
import com.panopset.compat.ApplicationInformation;
import com.panopset.compat.JavaVersionChart;
import com.panopset.compat.Jsonop;
import com.panopset.compat.Logop;
import com.panopset.compat.NameValuePair;
import com.panopset.compat.SortedMapKey;
import com.panopset.compat.Stringop;
import com.panopset.compat.TextScrambler;
import com.panopset.compat.UrlHelper;
import com.panopset.flywheel.FlywheelListDriver;
import com.panopset.flywheel.LineFeedRules;
import com.panopset.flywheel.ReflectionInvoker;
import com.panopset.flywheel.samples.FlywheelSamples;

@Controller
public class WebController {
  private static final String STATIC_HOST = "static_host";

  static final String[] PLATFORMS = { "linux", "mac", "win" };
  static final String[][] INSTALLERS = {

      { "linux", String.format("panopset_%s-1_amd64.deb", AppVersion.getVersion()) },
      { "mac", String.format("panopset-%s.dmg", AppVersion.getVersion()) },
      { "win", String.format("panopset-%s.msi", AppVersion.getVersion()) } };
 
  static final String INDEX = "index";

  static final String HOME = "home";
  static final String FLYWHEELINFO = "flywheelinfo";

  static final String DESK = "desk";
  static final String DOWNLOAD = "download";

  static final String UTILITIES = "utilities";
  static final String FLYWHEEL = "flywheel";
  static final String LOWERCLASS = "lowerclass";

  static final String SECURITY = "security";
  static final String SCRAMBLER = "scrambler";
  static final String CHECKSUM = "checksum";

  static final String GAMES = "games";
  static final String BLACKJACK = "blackjack";

  static final String NEWS = "news";
  static final String ABOUT = "about";
  static final String BUILD = "build";

  static final String VERSION = "version";
  static final String FW_FORM_ATTR = "fwInput";
  static final String PGDTA = "pageData";
  static final String PFK = "pfk";

  static final String CHART = "chart";
  static final String JAR_MAP = "jar_map";
  static final String INSTALLER_MAP = "installer_map";
  static final String HOMEDIRPFX = "homedirpfx";
  static final String USERPATHSEP = "userpathsep";
  static final String APPLIST = "applist";

  @Value("${pan.static}")
  private String staticBaseURL;

  @GetMapping("/error")
  public String error(Model model) {
    init(model);
    return "error";
  }

  @GetMapping({ "/", "/home", "/index", "/index.htm", "/index.html" })
  public String home(Model model, HttpServletResponse response) {
    init(model);
    return "index";
  }

  @GetMapping({"/desk", "/desk.htm", "/desk.html"})
  public String desk(HttpServletRequest request, Model model, HttpServletResponse response) {
    init(model);
    setupHomeDirPfx(request, model);
    return DESK;
  }

  private Type listType;
  private Type getListType() {
    if (listType == null) {
      listType = new TypeToken<ArrayList<ApplicationInformation>>() {
      }.getType();
    }
    return listType;
  }

  @GetMapping({"/download", "/download.htm", "/download.html"})
  public String download(HttpServletRequest request, Model model, HttpServletResponse response) {
    init(model);
    model.addAttribute(VERSION, AppVersion.getVersion());
    model.addAttribute(PFK, PLATFORMS);
    model.addAttribute(JAR_MAP, getPanopsetJarValidationMap());
    model.addAttribute(INSTALLER_MAP, getInstallerValidationMap());
    setupHomeDirPfx(request, model);
    return DOWNLOAD;
  }

  @GetMapping({"/build", "/build.htm", "/build.html"})
  public String build(Model model, HttpServletResponse response) {
    init(model);
    return BUILD;
  }

  @GetMapping({"/news", "/news.htm", "/news.html"})
  public String news(HttpServletRequest request, Model model, HttpServletResponse response) {
    init(model);
    return NEWS;
  }

  @GetMapping({"/about", "/about.htm", "/about.html"})
  public String about(Model model, HttpServletResponse response) {
    init(model);
    return ABOUT;
  }

  @GetMapping({ "/design", "/design.htm", "/design.html" })
  public String design(Model model, HttpServletResponse response) {
    init(model);
    return "design";
  }

  @GetMapping({ "/flywheel", "/flywheel.htm", "/flywheel.html" })
  public String flywheel(Model model, HttpServletResponse response) {
    init(model);
    FwInput fwInput = new FwInput();
    model.addAttribute(FW_FORM_ATTR, fwInput);
    model.addAttribute("result", "");
    model.addAttribute("fwss", FlywheelSamples.all());
    model.addAttribute("fwfs", ReflectionInvoker.getAll());
    return FLYWHEEL;
  }

  @GetMapping({ "/lowerclass", "/lowerclass.htm", "/lowerclass.html" })
  public String lowerclass(Model model, HttpServletResponse response) {
    init(model);
    model.addAttribute(CHART, JavaVersionChart.getChart());
    return LOWERCLASS;
  }

  @GetMapping({ "/scrambler", "/scrambler.htm", "/scrambler.html" })
  public String scrambler(Model model, HttpServletResponse response) {
    init(model);
    return SCRAMBLER;
  }

  @GetMapping({ "/checksum", "/checksum.htm", "/checksum.html" })
  public String checksum(Model model, HttpServletResponse response) {
    init(model);
    return CHECKSUM;
  }

  @GetMapping({ "/blackjack", "/blackjack.htm", "/blackjack.html" })
  public String blackjack(Model model, HttpServletResponse response) {
    init(model);
    return BLACKJACK;
  }

  @PostMapping("/af")
  public ResponseEntity<String> getResult(@RequestBody FwInput fwInput, HttpServletResponse response) {
    String result;
    List<String> listParam = Stringop.stringToList(fwInput.getListStr());
    try {
      result = new FlywheelListDriver.Builder(listParam, fwInput.getTemplate())
          .withLineFeedRules(new LineFeedRules(fwInput.getLineBreakStr(), fwInput.getListBreakStr()))
          .withTokens(fwInput.getTokens()).withSplitz(fwInput.getSplitz()).build().getOutput();
    } catch (IOException e) {
      result = e.getMessage();
    }
    return ResponseEntity.ok(result);
  }

  @PostMapping("/scramble")
  public ResponseEntity<String> scramble(@RequestBody ScramblerInput si, HttpServletResponse response) {
    String result;

    String text = si.getText();
    String koi = si.getKoi();
    String passphrase = si.getPassphrase();
    int k = Stringop.parseInt(koi, 10000);
    try {
      result = new TextScrambler().withKeyObtentionIters(k).encrypt(passphrase, text);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    return ResponseEntity.ok(result);
  }

  @PostMapping("/unscramble")
  public ResponseEntity<String> unscramble(@RequestBody ScramblerInput si, HttpServletResponse response) {
    String result;

    String text = si.getText();
    String koi = si.getKoi();
    String passphrase = si.getPassphrase();
    int k = Stringop.parseInt(koi, 10000);
    try {
      result = new TextScrambler().withKeyObtentionIters(k).decrypt(passphrase, text);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    return ResponseEntity.ok(result);
  }

  private Map<String, List<NameValuePair>> panopsetJarMap;

  @SuppressWarnings("unchecked")
  private Map<String, List<NameValuePair>> getPanopsetJarValidationMap() {
    if (panopsetJarMap == null) {
      panopsetJarMap = Collections.synchronizedSortedMap(new TreeMap<>());
      for (String platform : PLATFORMS) {
        List<NameValuePair> cil = new ArrayList<>();
        String pci = UrlHelper.getTextFromURL(String.format("%sgen/json/pci_%s.json", getStaticBaseURL(), platform));
        if (Stringop.isPopulated(pci)) {
          cil = (List<NameValuePair>) new Jsonop().fromJson(pci, cil.getClass());
          safeAdd(new String[] { platform, "panopset.jar" }, panopsetJarMap, cil);
        }
      }
    }
    return panopsetJarMap;
  }

  private Map<String, List<NameValuePair>> installerMap;

  @SuppressWarnings("unchecked")
  private Map<String, List<NameValuePair>> getInstallerValidationMap() {
    if (installerMap == null) {
      installerMap = Collections.synchronizedSortedMap(new TreeMap<>());
      for (String[] installers : INSTALLERS) {
        List<NameValuePair> cil = new ArrayList<>();
        String url = String.format("%sgen/json/pci_%s.json", getStaticBaseURL(), installers[1]);
        Logop.info(String.format("%s : %s, %s", installers[0], installers[1], url));
        String pci = UrlHelper.getTextFromURL(url);
        if (Stringop.isPopulated(pci)) {
          cil = (List<NameValuePair>) new Jsonop().fromJson(pci, cil.getClass());
          if (cil == null || cil.isEmpty()) {
            Logop.dspmsg(String.format("URL found, but still unable to load installers from %s.", url));
          }
          safeAdd(installers, installerMap, cil);
        } else {
          Logop.dspmsg(String.format("Unable to load installers from %s.", url));
        }
      }
    }
    return installerMap;
  }

  private void safeAdd(String[] definition, Map<String, List<NameValuePair>> map, List<NameValuePair> value) {
    if (value != null && value.size() > 3) {
      map.put(definition[0], value);
    } else {
      map.put(definition[0], createDummyList(definition[1]));
    }
  }

  private List<NameValuePair> createDummyList(String missing) {
    List<NameValuePair> dummyList = new ArrayList<>();
    dummyList.add(new NameValuePair("version", "1.0.1"));
    dummyList.add(new NameValuePair("bytes", "0"));
    dummyList.add(new NameValuePair(missing, "not found"));
    dummyList.add(new NameValuePair("ifn", missing));
    return dummyList;
  }

  private String getStaticBaseURL() {
    return staticBaseURL;
  }

  private void setupHomeDirPfx(HttpServletRequest request, Model model) {
    var ua = request.getHeader("User-Agent");
    if (ua.toLowerCase().indexOf("win") > -1) {
      model.addAttribute(HOMEDIRPFX, "%USERPROFILE%\\");
      model.addAttribute(USERPATHSEP, "\\");
    } else {
      model.addAttribute(HOMEDIRPFX, "~/");
      model.addAttribute(USERPATHSEP, "/");
    }
  }
  
  private void init(Model model) {
    model.addAttribute(STATIC_HOST, getStaticBaseURL());
  }
}
