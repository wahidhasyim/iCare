package com.example.administrator.icare.Model;

public class ListBerandaModel {

        private String username;
        private String status;
        private String timestamp;
        private String image;
        private String IdStatus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdStatus() {
            return IdStatus;
        }

        public void setIdStatus(String idStatus) {
            IdStatus = idStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }


