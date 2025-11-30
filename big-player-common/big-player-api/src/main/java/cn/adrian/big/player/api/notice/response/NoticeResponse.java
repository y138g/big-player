package cn.adrian.big.player.api.notice.response;

import cn.adrian.big.player.base.response.BaseResponse;

/**
 * 通知统一返回类
 * @author Adrian
 */
public class NoticeResponse extends BaseResponse {

    public static class Builder {

        private final NoticeResponse response;

        public Builder() {
            response = new NoticeResponse();
        }

        public Builder setCode(String code) {
            response.setResponseCode(code);
            return this;
        }

        public Builder setMessage(String message) {
            response.setResponseMessage(message);
            return this;
        }

        public Builder setSuccess(boolean success) {
            response.setSuccess(success);
            return this;
        }

        public NoticeResponse build() {
            return response;
        }
    }
}
