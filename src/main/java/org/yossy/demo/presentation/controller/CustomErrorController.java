package org.yossy.demo.presentation.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")  // この設定でSpringデフォルトのerror.htmlを無効
@Slf4j
public class CustomErrorController implements ErrorController {

    /**
	   * HTML レスポンス用の ModelAndView オブジェクトを返す。
	   *
	   * @param req リクエスト情報
	   * @param mav レスポンス情報
	   * @return HTML レスポンス用の ModelAndView オブジェクト
	   */
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView httpEror(HttpServletRequest req, ModelAndView mav) {
 
		// HTTP ステータスを決める
		HttpStatus status = getHttpStatus(req);
 
		// HTTP ステータスをセットする
		mav.setStatus(status);
 
		// ビュー名にerror.htmlをセット
		mav.setViewName("error");
 
		mav.addObject("timestamp", LocalDate.now());
		mav.addObject("status", status.value());
		mav.addObject("path", req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
 
		// ステータスに応じて処理

		// 404 Not Found
		if (status == HttpStatus.NOT_FOUND) return mav.addObject("message", "Not Found");

		// 404 以外は500 Internal Server Error とする
		return mav.addObject("message", "System Error!");
	}

    	/**
	 * JSON レスポンス用の ResponseEntity オブジェクトを返す。
	 *
	 * @param req リクエスト情報
	 * @return JSON レスポンス用の ResponseEntity オブジェクト
	 */
	@RequestMapping
	public ResponseEntity<Map<String, Object>> errorJson(HttpServletRequest req) {
 
		// エラー情報を取得
		Map<String, Object> attr = getErrorAttributes(req);
 
		// HTTP ステータスを決める
		HttpStatus status = getHttpStatus(req);
 
		// 出力したい情報をセットする
		Map<String, Object> body = new HashMap<>();
		body.put("status", status.value());
		body.put("timestamp", attr.get("timestamp"));
		body.put("error", attr.get("error"));
		body.put("exception", attr.get("exception"));
		body.put("message", attr.get("message"));
		body.put("errors", attr.get("errors"));
		body.put("trace", attr.get("trace"));
		body.put("path", attr.get("path"));
 
		// 情報を JSON で出力する
		return new ResponseEntity<>(body, status);
	}
    
    /**
     * トップページ
     *
     * @return index
     */
    @GetMapping(value = "/session_maxover")
    public String index() {
        log.info("Session Max over!");
        return "/error/session_maxover";
    }
 
	/**
	  * JSON レスポンス用の エラー情報を抽出する。
	  *
	  * @param req リクエスト情報
	  * @return エラー情報
	  */
	private Map<String, Object> getErrorAttributes(HttpServletRequest req) {
		// DefaultErrorAttributes クラスで詳細なエラー情報を取得する
		ServletWebRequest swr = new ServletWebRequest(req);
		DefaultErrorAttributes dea = new DefaultErrorAttributes();
		ErrorAttributeOptions eao = ErrorAttributeOptions.of(
				ErrorAttributeOptions.Include.BINDING_ERRORS,
				ErrorAttributeOptions.Include.EXCEPTION,
				ErrorAttributeOptions.Include.MESSAGE,
				ErrorAttributeOptions.Include.STACK_TRACE);
		return dea.getErrorAttributes(swr, eao);
	}

    /**
	 * レスポンス用の HTTP ステータスを決める。
	 *
	 * @param req リクエスト情報
	 * @return レスポンス用 HTTP ステータス
	 */
	private static HttpStatus getHttpStatus(HttpServletRequest req) {
		// HTTP ステータスを決める
		// ここでは 404 以外は全部 500 にする
		Object statusCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (statusCode != null && statusCode.toString().equals("404")) {
			status = HttpStatus.NOT_FOUND;
		}
		return status;
	}
}