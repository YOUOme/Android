# YOUOme - Open Banking Sample App

## 테스트용 샘플앱 소스(안드로이드, iOS)
이용기관(핀테크기업)에서 앱 제작 시 API 호출 예제를 참고할 수 있는 샘플앱 소스입니다.

본 샘플앱은 은행권 공동 오픈플랫폼에서 제공하는 API를 활용한 앱 개발 및 테스트에 도움을 드리기 위해 제작되었습니다.

자세한 사항은 첨부파일(샘플앱 매뉴얼)을 참고하시기 바랍니다.

* 주의 : 반드시 자신의 앱 키(Key)와 시크릿(Secret)으로 설정 후 테스트를 진행하셔야 합니다.

<다운로드 경로>
https://test.bankwallet.co.kr/test_distribution/op_sample_app.html


[이용기관 샘플앱 소스 적용 방법] : 안드로이드

(1) 이용기관 샘플앱 소스의 압축을 특정 폴더에 해제합니다.

(2) Android Studio 프로젝트를 신규 생성 후 디폴트로 생성되는 샘플소스들을 제거합니다.

(3) 생성된 프로젝트 디렉토리의 module 디렉토리(예: 프로젝트폴더\UseOrgSampleApp\app)에 
      압축을 해제했던 src 폴더를 붙여넣어(overwrite) 병합합니다.

(4) 이후 각 환경에 따라 발생할 수 있는 build path 문제 등을 해결하며 빌드를 시도합니다.

* 참고사항 : 

- JDK는 1.8 버전을 사용하여 작업하였습니다.

- Android Studio 2.3 기준으로 프로젝트를 생성하여, 작업했던 디렉토리 구조의 일부를 발췌해서 배포하였습니다.
   (예) project명: UseOrgSampleApp / module명: app

- build.gradle 파일(module level)을 첨부한 이유는, 빌드오류 시 dependencies 참고를 위해서 제공한 것으로, 반드시 동일하게 해야하는 것은 아닙니다.

- 샘플앱 작업 시 사용했던 minSdkVersion, targetSdkVersion 등에 대해서는 build.gradle 파일을 참고해 주시기 바랍니다.