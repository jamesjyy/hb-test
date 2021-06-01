package cn.best.approveservice.response;

import org.springframework.stereotype.Component;

/**
 * Title: 统一响应结构
 * Description:使用REST框架实现前后端分离架构，我们需要首先确定返回的JSON响应结构是统一的，
 * 也就是说，每个REST请求将返回相同结构的JSON响应结构。不妨定义一个相对通用的JSON响应结构，其
 * 中包含两部分：元数据与返回值，其中，元数据表示操作是否成功与返回值消息等，返回值对应服务端方法所返回的数据。
 * { "msg": { "success": true, "message": "ok" }, "data": ... }
 *
 */
@Component
public class Response {

	private static final String OK = "ok";
	private static final String ERROR = "error";

	private Msg msg;     // 元数据
	private Object data;   // 响应内容

	public Response success() {
		this.msg = new Msg(true, OK);
		return this;
	}

	public Response success(Object data) {
		this.msg = new Msg(true, OK);
		this.data = data;
		return this;
	}

	public Response failure() {
		this.msg = new Msg(false, ERROR);
		return this;
	}

	public Response failure(String message) {
		this.msg = new Msg(false, message);
		return this;
	}

	public Msg getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}

	/**
	 * Title: 请求元数据
	 * @author hai
	 * @created 2017年7月4日 下午5:08:12
	 */
	public class Msg {

		private boolean success;
		private String message;

		public Msg(boolean success) {
			this.success = success;
		}

		public Msg(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}
	}
}
