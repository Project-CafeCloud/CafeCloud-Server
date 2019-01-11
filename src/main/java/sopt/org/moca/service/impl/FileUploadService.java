package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileUploadService {

    //버킷 이름 동적 할당
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //버킷 주소 동적 할당
    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    private final S3Service s3Service;

    public FileUploadService(final S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public String upload(MultipartFile uploadFile, String dir) throws IOException {
        String origName = uploadFile.getOriginalFilename();
        String url;

        try {
            //확장자
            String ext = origName.substring(origName.lastIndexOf('.'));
            //파일이름 암호화
            String saveFileName = getUuid() + ext;
            //파일 객체 생성
            File file = new File(System.getProperty("user.dir") + saveFileName);
            //파일 변환
            uploadFile.transferTo(file);
            //S3 파일 업로드
            s3Service.uploadOnS3(dir, saveFileName, file);

            //주소 할당
            if(dir.compareTo("message") == 0){
                url = defaultUrl + dir + "/" + saveFileName;
            } else {
                url = dir + "/" + saveFileName;
            }

            //파일 삭제
            file.delete();
        } catch (StringIndexOutOfBoundsException e) {
            //파일이 없을 경우 예외 처리
            url = null;
        }

        return url;
    }

    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}