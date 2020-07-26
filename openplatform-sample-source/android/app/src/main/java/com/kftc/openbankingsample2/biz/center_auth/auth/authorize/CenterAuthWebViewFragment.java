package com.kftc.openbankingsample2.biz.center_auth.auth.authorize;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.common.util.Utils;

import java.io.File;
import java.util.Map;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;


/**
 * 사용자인증 개선버전에서 공통적으로 사용하는 WebView Fragment
 */
public class CenterAuthWebViewFragment extends AbstractCenterAuthMainFragment {

    public static final String URL_TO_LOAD = "urlToLoad";

    // context
    private Context context;

    // view
    private View view;
    private WebView webView;

    // data
    private Bundle args;
    private String urlToLoad;
    private String state;
    private Map<String, String> headerMap;

    ////////// 인증서 관련 시작 //////////
    // CallBack FilePath
    private ValueCallback<Uri[]> filePath;
    // 인증서 저장 기본 디렉토리(변경가능)
    private static final String CERT_DEFAULT_DIR = "NPKI/yessign/USER";
    // 인증서 파일 전송 기본 mime-type
    private static final String CERT_DEFAULT_MIME_TYPE = "*/*";
    // 인증서 가져오기 타이틀
    private static final String CERT_INTENT_TITLE = "인증서 가져오기";
    // 인증서 관련 user agent
    private static final String CERT_USER_AGENT_YESSIGN_ANDROID = "\u0020yessign/wv/A-8GYT7FZ5Qb37lB4Ud4Lt;1.0";
    ////////// 인증서 관련 끝 //////////


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();
        if (args == null) args = new Bundle();

        urlToLoad = args.getString(URL_TO_LOAD, "");
        state = args.getString(CenterAuthConst.BUNDLE_KEY_STATE, "");
        headerMap = (Map<String, String>) args.getSerializable("headerMap");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_center_auth_webview, container, false);
        initView();
        return view;
    }

    void initView() {
        EditText etUrl = view.findViewById(R.id.etUrl);
        etUrl.setText(urlToLoad);
        Button btnFold = view.findViewById(R.id.btnFold);
        btnFold.setOnClickListener(v -> {
            if("펼침".equals(btnFold.getText().toString())){
                etUrl.setSingleLine(false);
                btnFold.setText("접힘");
            }else{
                etUrl.setSingleLine(true);
                btnFold.setText("펼침");
            }
        });

        // 사용자이름 필드를 url encoding 한다 (G/W에서 디코딩 해 주는 설정 있음)
        if(headerMap != null){
            String userName = headerMap.get("Kftc-Bfop-UserName");
            if (userName != null) {
                headerMap.put("Kftc-Bfop-UserName", Utils.urlEncode(userName));
            }
        }

        webView = view.findViewById(R.id.webView);
        webView.setNetworkAvailable(true);

        // 테스트
        webView.setWebContentsDebuggingEnabled(true);

        loadUrlOnWebView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.d("#### (AUTH) onActivityResult : %d, %d", requestCode, resultCode);
        if (resultCode == RESULT_OK) {
            if (filePath == null) return;
            filePath.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            filePath = null;
        } else {
            if (filePath != null) {
                filePath.onReceiveValue(null);
                filePath = null;
            }
        }
    }

    void loadUrlOnWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDefaultTextEncodingName("UTF-8");

        ////////// 브라우저 인증서 사용을 위한 옵션 시작 //////////
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // 일부폰에서 접근성 설정에 의한 css 깨짐 발생방지
        webSettings.setTextZoom(100);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 모바일 브라우저와 웹뷰(하이브리드 앱)를 구분하기 위한 UserAgent 설정
        StringBuffer sb = new StringBuffer(webSettings.getUserAgentString()).append(CERT_USER_AGENT_YESSIGN_ANDROID);
        Timber.d("#### (AUTH) agent string : %s", sb.toString());
        webSettings.setUserAgentString(sb.toString());
        ////////// 브라우저 인증서 사용을 위한 옵션 끝 //////////

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                showAlert("확인", message, null, (dialog, which) -> result.confirm());
                return true;
            }

            // 브라우저 인증서 사용을 위해, 새창을 띄우기 위한 설정(자세히보기, 개인정보수집이용 약관 등)
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                Timber.d("#### (AUTH) onCreateWindow");
                WebView newWebView = new WebView(context);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Timber.d("#### (AUTH) newWebView url : %s", url);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(url));
                        startActivity(browserIntent);
                        return true;
                    }
                });
                return true;
            }

            // 인증서를 파일탐색기에서 가져오기 위한 설정
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Timber.d("#### (AUTH) onShowFileChooser");
                if (filePath != null) {
                    filePath.onReceiveValue(null);
                    filePath = null;
                }
                filePath = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                String dirStr = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + CERT_DEFAULT_DIR + File.separator;
                //String dirStr = context.getExternalFilesDir(null).getAbsolutePath() + File.separator + CERT_DEFAULT_DIR + File.separator;
                Timber.d("#### (AUTH) dirStr : %s", dirStr);
                Uri uri = Uri.parse(dirStr);
                intent.setDataAndType(uri, CERT_DEFAULT_MIME_TYPE);
                startActivityForResult(Intent.createChooser(intent, CERT_INTENT_TITLE), 0);
                return true;
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Timber.d("#### (AUTH) onConsoleMessage : %d-%s-%s", consoleMessage.lineNumber(), consoleMessage.message(), consoleMessage.sourceId());
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Timber.d("#### (AUTH) webView url : %s", url);

                /*
                 * AuthorizationCode 발급이 완료된 이후에, 해당 코드를 사용하여 토큰발급까지의 흐름을 UI상에 보여주기 위해서 추가한 코드 예시
                 * 이용기관에서는 redirect uri 에 해당하는 페이지에서 에러처리를 해야한다.
                 */
                String callbackUrl = CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_REDIRECT_URI);
                if(url.startsWith(callbackUrl)){

                    // error 코드가 있을 경우 에러처리
                    String error = Utils.getParamValFromUrlString(url, "error");
                    if (!error.isEmpty()) {
                        String errorDesc = Utils.getParamValFromUrlString(url, "error_description");
                        if (errorDesc.isEmpty()) {
                            errorDesc = "알 수 없는 오류가 발생하였습니다.";
                        }
                        showAlert("인증 에러코드 : " + error, Utils.urlDecode(errorDesc), url, (dialog, which) -> onBackPressed());
                        return true;
                    }

                    // error 코드가 없으면 토큰발급으로 이동
                    String code = Utils.getParamValFromUrlString(url, "code");
                    String scope = Utils.getParamValFromUrlString(url, "scope");
                    String client_info = Utils.getParamValFromUrlString(url, "client_info");

                    // 요청시 이용기관이 세팅한 state 값을 그대로 전달받는 것으로, 이용기관은 CSRF 보안위협에 대응하기 위해 요청 시의 state 값과 응답 시의 state 값을 비교해야 함
                    String returnState = Utils.getParamValFromUrlString(url, "state");
                    if (!returnState.equals(state)) {
                        showAlert("상태난수값 오류", "보낸 난수값과 서버로부터 받은 난수값이 서로 일치하지 않습니다.", "보낸 난수값: " + state + "\n\n받은 난수값: " + returnState);
                        return true;
                    }

                    args.putString("code", code);
                    args.putString("scope", scope);
                    args.putString("client_info", client_info);
                    args.putString("state", state);
                    goNext();
                    return true;
                }

                // PASS intent URI 호출 지원(앱이 설치되어있으면 앱이 실행되도록 하기 위함)
                if (url.startsWith("intent://") || url.startsWith("tauthlink://") || url.startsWith("ktauthexternalcall://") || url.startsWith("upluscorporation://")) {
                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        startActivity(intent);
                    } catch (Exception e) {
                            Timber.e(e);
                    }
                    return true;
                }

                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Timber.d("#### (AUTH) onReceivedError: %s, %s", error.getErrorCode(), error.getDescription());
                } else {
                    Timber.d("#### (AUTH) onReceivedError");
                }
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
                Timber.d("#### (AUTH) onRreceivedLoginRequest");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Timber.d("#### (AUTH) onReceivedSslError : %s", error.getCertificate().toString());

                // 테스트에서만 사용
                handler.proceed(); // Ignore SSL certificate errors
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgress();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideProgress();

                /*String script = "(function() {" +
                            "return ('<html>' + document.getElementsByTagName('html')[0].innerHTML+'</html>');" +
                        "})();";
                view.evaluateJavascript(script, value -> {
                    String html = value.replaceAll("\\\\u003C", "<");
                    String html2 = html.replaceAll("\\\\\"", "");
                    Timber.d(html2);
                });*/
            }
        });

        // 인증서 관련 쿠키 설정(크로스사이트 문제). 인증서를 사용하는 경우, 이 옵션이 없으면 클라우드 서비스 연결안됨
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

        webView.loadUrl(urlToLoad, headerMap);

    }

    // 프로바이더 페이지의 뒤로가기를 사용할 경우
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    void goNext() {
        startFragment(CenterAuthTokenRequestFragment.class, args, R.string.fragment_id_token);
    }
}
