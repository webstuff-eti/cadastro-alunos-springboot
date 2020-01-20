package br.eti.webstuff.CadastroAlunos.web.controllers.error;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundDetails extends ErrorDetails{

    private String title;
    private int status;
    private String detail;
    private Long timestamp;
    private String developerMessage;

    //Criar apenas métodos getters vém da herança
//    public String getTitle() {
//        return title;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public String getDetail() {
//        return detail;
//    }
//
//    public Long getTimestamp() {
//        return timestamp;
//    }
//
//    public String getDeveloperMessage() {
//        return developerMessage;
//    }

    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResourceNotFoundDetails build() {
            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            resourceNotFoundDetails.setDeveloperMessage(developerMessage);
            resourceNotFoundDetails.setTitle(title);
            resourceNotFoundDetails.setDetail(detail);
            resourceNotFoundDetails.setTimeStamp(timestamp);
            resourceNotFoundDetails.setStatus(status);
            return resourceNotFoundDetails;
        }
    }
}
