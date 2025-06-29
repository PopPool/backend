## 팝풀(PopPool)
**흩어져 있는 팝업스토어 정보를 제공하고 인스타그램 연동으로 부가 기능을 제공하는 서비스**

[앱 스토어 링크](https://apps.apple.com/kr/app/%ED%8C%9D%ED%92%80-poppool/id6740803210)

<p align="left">
  <img src="https://avatars.githubusercontent.com/u/171132535?s=200&v=4"/>
</p>

## 목차
1. 프로젝트 멤버 소개
2. 서비스 소개
3. 기술 스택
4. 시스템 아키텍처
5. 주요 기능
6. 협업 관리
7. 프로젝트 기간
8. 프로젝트 관련 문서
</br>

## 👪 프로젝트 멤버 소개
<p align="center">
  <table>
      <tr>
        <td>
          <img src="https://avatars.githubusercontent.com/u/134565003?v=4" width="120px" height="120px" />
        </td>
        <td>
          <img src="https://avatars.githubusercontent.com/u/171133308?v=4" width="120px" height="120px" />
        </td>
        <td>
          <img src="https://avatars.githubusercontent.com/u/119504454?v=4" width="120px" height="120px" />
        </td>
        <td>
          <img src="https://avatars.githubusercontent.com/u/112812473?v=4" width="120px" height="120px" />
        </td>
        <td>
          <img src="https://avatars.githubusercontent.com/u/122965360?v=4" width="120px" height="120px" />
        </td>
        <td>
          <img src="https://avatars.githubusercontent.com/u/73388615?v=4" width="120px" height="120px" />
        </td>
      </tr>
      <tr>
          <td height="30px" width="120px" align="center"> <a href="https://github.com/chae-rok">
           🙂 김채연 <br>(기획) </a> <br></td>
          <td height="30px" width="120px" align="center"> <a href="https://github.com/jaeeun0416">
           🙂 이재은 <br>(디자이너) </a> <br></td>
          <td height="30px" width="120px" align="center"> <a href="https://github.com/Madman-dev">
           🙂 이동준 <br>(iOS) </a> <br></td>
          <td height="30px" width="120px" align="center"> <a href="https://github.com/dongglehada">
           🙂 서준영 <br>(iOS) </a> <br></td>
          <td height="30px" width="120px" align="center"> <a href="https://github.com/zzangzzangguy">
           🙂 김기현 <br>(iOS) </a> <br></td>
          <td height="30px" width="120px" align="center"> <a href="https://github.com/JaesungYoun">
           🙂 윤재성 <br>(backend) </a> <br></td>
      </tr>
  </table>
</p>

## ✏️ 기획 배경

- **팝업스토어 시장 규모 확대 및 소비자 관심 증대**
    
    
    - 높은 팝업스토어 방문 경험
            
      ***75.6% 팝업스토어 방문 경험 있다***

      <img src="https://github.com/user-attachments/assets/b569ac96-7ab9-40eb-93d3-6539558e1372" width="300" height="500">

        
    - 팝업스토어 방문 계기 & 방문 목적
            
      ***“그냥 지다가던 길에 우연히” ⇒ 단순 호기심에 방문하는 경향 뚜렷***
            
        - 하지만 팝업스토어 방문 경험자의 절반 가량(53.6%)이 가고 싶은 곳을 일부러 찾아간 적이 있고, 
        - 전시나 체험 요소가 많은 팝업스토어에서는 오래 머물고 싶다(55.6%, 동의율)는 응답이 과반으로 평가되는 등 

      ***팝업스토어에서만 느낄 수 있는 새로운 경험에 대한 니즈도 높은 것으로 파악됨***



      <img src="https://github.com/user-attachments/assets/0913210b-2031-4272-87dd-dcdd1687d22d" width="500" height="300">
            
            
    
    - 기업의 긍정적인 팝업스토어 마케팅 효과
        
            
      ***81.3% 팝업스토어는 브랜드나 콘텐츠를 소비자에게 각인 시키는 데 효과적인 것 같다***


      <img src="https://github.com/user-attachments/assets/4d6c855e-7280-4f70-b034-7ec8b08ac040" width="300" height="500">
      
    
- **젊은 연령층일수록 팝업스토어에 대한 관심도와 만족도가 높음**
    
    
    - 팝업스토어 방문 후 만족도
        
      - **저연령일수록** 팝업스토어 방문 후의 만족감이 **높음**
            

        <img src="https://github.com/user-attachments/assets/19dc271f-2c44-4358-a5e3-4ee3f5d7ba7f" width="500" height="300">
            
    
    - 2030 세대의 높은 관심도
            
      ***53.6% 내가 가고 싶은 팝업스토어에 방문하기 위해 ‘일부러 찾아간 경험이 있다.***
 
      <img src="https://github.com/user-attachments/assets/f94dff42-f348-4b32-872f-962b8bf40547" width="300" height="500">
      
    
    - 팝업스토어 정보는 주로 “인스타그램”
      - 1020 세대는 대부분 행사, 이벤트, 핫플 소식 소개하는 ‘인스타그램 계정’에서 정보를 얻음
      - 인스타그램은 올해 이용자들의 편의를 높이는 기능들을 추가했다. 이에 따라 크리에이터는 물론 비즈니스 계정들이 마케팅을 위해 사용하는 사례가 증가했다. *특히 Z세대가 방문 인증하기 좋아하는 ‘팝업스토어’를 소개하는 계정이 늘었다.*(뉴스기사 출처)
     


## Target

- **`SOM`**  **팝풀 초기 사용자** 
서울 및 주요 대도시에 거주하는 20-30대로 인스타그램을 활발히 사용하는 소셜 미디어 유저들
- **`SAM`** **국내 팝업스토어 관심자** 
주로 20-30대 젊은 층으로 특별한 쇼핑 경험을 추구하는 소비자들
- **`TAM`** **전 세계 팝업스토어 관심자** 
팝업스토어에 관심 있는 모든 사람들로 특히 트렌디한 쇼핑 경험을 즐기는 소비자들

## 💡 서비스 소개
**흩어져 있는 팝업스토어 정보를 제공하고 인스타그램 연동으로 부가 기능을 제공하는 앱 서비스**
>PopPool은 PopUpStore + Pool 의 약자로, 팝업의 장이라는 의미의 이름입니다.</br>
### **Painpoint**

- 흩어져 있는 팝업 스토어 정보를 관심사 기반으로 정리하여 제공해주는 서비스가 없다.
- 대부분 SNS 특히 인스타그램에서 팝업스토어 정보를 수집하는데 필요한 때에 필요한 정보와 후기를 찾기가 쉽지 않다.
    - 다양한 계정에서 얻은 정리되지 않은 북마크한 정보들
    - 가장 영향도 높은 인스타그램 후기 정보 탐색의 어려움

### **Solution**

- 관심사 기반의 팝업스토어 정보를 추천해주는 서비스
- 북마크 정보를 뒤적이지 않아도, 다양한 앱과 계정을 옮겨다니지 않아도 하나의 앱에서 관련 정보와 후기까지 한 번에 볼 수 있는 서비스

</br>

## 📕 주요 기능

1. **관심사 기반 팝업스토어 큐레이션** 
    1. “관심사, 성별, 연령대”만 알려주세요! 나머지는 팝풀이 할게요. 
    더이상 인스타그램 저장함을 뒤져서 북마크한 콘텐츠를 찾지 않아도, 사진첩을 올려서 캡쳐한 사진을 찾지 않아도 돼요. 팝풀이 딱 맞는 팝업스토어를 추천해드릴게요:)  
        1. 📍 팝풀이님을 위한 맞춤 팝업 큐레이션 : ‘성별, 연령대, 관심사’ 기준으로 개인별 최근 본 팝업스토어의 데이터와 활동 이력을 분석해 맞춤 팝업스토어를 추천합니다.
        2. 📍 팝풀이들은 지금 이런 팝업에 가장 관심 있어요! : 팝풀 유저들이 가장 많이 찾아보고 활동한 가장 인기있는 팝업스토어 Top 10을 만나보세요! 
        3. 📍 제일 먼저 올리는 신규 오픈 팝업 : 신규 오픈한지 2주가 되지 않은 신상 팝업스토어만 모아서 보여드려요. 
        
2. **쉽고 빠른 팝업스토어 탐색**
    1. 내 주변 혹은 원하는 위치에 어떤 팝업스토어가 있는지 지도에서 한 눈에 볼 수 있어요! 
        1. 지역과 카테고리 옵션을 선택해 원하는 팝업스토어 정보만 골라 지도에서 볼 수 있어요
        2. 물론, 전체 목록으로도 볼 수 있습니다.
        
    2. 현재는 팝업스토어 이름만 검색할 수 있지만, 팝풀 유저들이 원하는 조건으로 검색할 수 있도록 검색엔진을 계속해서 고도화 할 예정이에요!  
    
3. **생생한 코멘트**
    1. 팝업스토어를 다녀온 팝풀 유저들이 남긴 생생한 후기와 기대평을 만나보세요!
        1. 📍 팝업스토어에 대한 궁금증, 기대평 그리고 생생한 후기까지 코멘트와 사진을 자유롭게 남길 수 있어요. 
        2. 📍 팝업스토어 방문 후 인스타그램에 올렸던 팝업스토어 사진을 후기로 올리고 싶은데 사진이 어디있지? 
        앨범을 다시 찾아보지 않아도 가져오고 싶은 인스타그램 콘텐츠의 링크만 복사 붙여넣기만 해주면 그대로 사진을 가져올 수 있어요:) 
        3. 📍 도움이 됐다고 생각하는 다른 사람의 코멘트에 ‘도움돼요’ 버튼을 눌러 반응을 남겨주세요. 반응이 많아질수록 더 정확한 팝업스토어 정보와 코멘트를 볼 수 있어요.
        
4. **둘러보기**
    1. 가입을 하지 않아도 팝풀이 제공하는 팝업스토어 정보를 탐색할 수 있어요!
    단, 팝풀 유저에게만 제공되는 다양한 서비스를 이용하려면 가입/로그인이 필수예요.

## 🛠️ 기술 스택

### iOS
![Swift](https://img.shields.io/badge/Swift-FA7343?style=flat&logo=swift&logoColor=white)
![UIkit](https://img.shields.io/badge/UIKit-007AFF?style=flat&logo=apple&logoColor=white)
![RxSwift](https://img.shields.io/badge/RxSwift-8A2C5A?style=flat&logo=react&logoColor=white)
![Clean Architecture](https://img.shields.io/badge/Clean%20Architecture-4CAF50?style=flat&logo=architecture&logoColor=white)
![MVVM](https://img.shields.io/badge/MVVM-FF5722?style=flat&logo=architecture&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)
</br>

### BackEnd
![Java](https://img.shields.io/badge/java-007396.svg?style=flat&logo=openjdk&logoColor=white)
![SpringBoot](https://img.shields.io/badge/springboot-%236DB33F.svg?style=flat&logo=springboot&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-%236DB33F.svg?style=flat&logo=JPA&logoColor=white)
![QueryDsl](https://img.shields.io/badge/QueryDsl-2185D0?style=flat&logo=QueryDsl&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=flat&logo=swagger&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=JSON%20web%20tokens)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=flat&logo=mysql&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=flate&logo=amazon-aws&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=flat&logo=jenkins&logoColor=white)
</br>

### 상세 기술스택 및 버전
| 구분     | 기술스택             | 상세내용                | 버전     |
|----------|---------------------|-------------------------|----------|
| 공통     | 형상관리             | Git                     |        |
|          | 커뮤니케이션         | Notion, Discord | -        |
| iOS | Framework       | -  UIkit                       | -        |
|     | Architecture    | -  Clean Architecture          | -        |
|     | Design Pattern  | -  MVVM + Input/Output Pattern | -        |
|     | Asnyc/Await     | -  RxSwift                     | -        |
|     | Network         | -  Alamofire / Network Layer   | -        |
|     | Asnyc/Await     | -  RxSwift                     | -        |
| BackEnd  | DB                  | MySQL                   | 8.0.32      |
|          |                     | JPA                     | \-          |
|          |                     | QueryDSL                | 5.0.0       |
|          | Java                | JDK                     | 17.0.12      |
|          | Spring              | Spring Boot             | 3.2.6       |
|          |                     | Spring Security         | 6.2.4       |
|          | JWT                 | jjwt                    | 0.11.5      |
|          | IDE                 | Intellij(Community)     | 23.3.5      |
|          | Build               | Gradle                  | 8.7         |
|          | API Docs            | OpenAPI(Swagger)        | 3.0.1       |
| Infra   | Server              | AWS EC2                 | -           |
|         | DB              | AWS RDS                 | -           |
|         | Object Storage              | AWS S3                 | -           |
| Server   | Deploy              | Jenkins                  | -           |
</br>

## 🗂️ 시스템 아키텍처
</br>


## 📁 Back-End 소스 코드 폴더 구조

```bash
src
├─ main
│  ├─ java
│  │  └─ com
│  │     └─ application
│  │        └─ poppool
│  │           ├─ domain
│  │           │  ├─ admin
│  │           │  │  ├─ notice
│  │           │  │  │  ├─ controller
│  │           │  │  │  │  ├─ AdminNoticeController.java
│  │           │  │  │  │  └─ AdminNoticeControllerDoc.java
│  │           │  │  │  ├─ dto
│  │           │  │  │  │  └─ request
│  │           │  │  │  │     ├─ CreateNoticeRequest.java
│  │           │  │  │  │     └─ UpdateNoticeRequest.java
│  │           │  │  │  └─ service
│  │           │  │  │     └─ AdminNoticeService.java
│  │           │  │  └─ popup
│  │           │  │     ├─ controller
│  │           │  │     │  ├─ AdminPopUpStoreController.java
│  │           │  │     │  └─ AdminPopUpStoreControllerDoc.java
│  │           │  │     ├─ dto
│  │           │  │     │  ├─ request
│  │           │  │     │  │  ├─ CreatePopUpStoreRequest.java
│  │           │  │     │  │  └─ UpdatePopUpStoreRequest.java
│  │           │  │     │  └─ response
│  │           │  │     │     ├─ GetAdminPopUpStoreDetailResponse.java
│  │           │  │     │     └─ GetAdminPopUpStoreListResponse.java
│  │           │  │     └─ service
│  │           │  │        └─ AdminPopUpStoreService.java
│  │           │  ├─ auth
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ AuthController.java
│  │           │  │  │  └─ AuthControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ info
│  │           │  │  │  │  ├─ ApplePublicKeys.java
│  │           │  │  │  │  └─ KakaoToken.java
│  │           │  │  │  ├─ request
│  │           │  │  │  │  ├─ AppleLoginRequest.java
│  │           │  │  │  │  └─ KakaoLoginRequest.java
│  │           │  │  │  └─ response
│  │           │  │  │     └─ LoginResponse.java
│  │           │  │  ├─ enums
│  │           │  │  │  ├─ SocialType.java
│  │           │  │  │  └─ TokenType.java
│  │           │  │  ├─ repository
│  │           │  │  │  └─ RefreshTokenRepository.java
│  │           │  │  └─ service
│  │           │  │     ├─ apple
│  │           │  │     │  ├─ AppleAuthFeignClient.java
│  │           │  │     │  └─ AppleAuthService.java
│  │           │  │     └─ kakao
│  │           │  │        ├─ KakaoAuthFeignClient.java
│  │           │  │        └─ KakaoAuthService.java
│  │           │  ├─ aws
│  │           │  │  └─ health
│  │           │  │     └─ HealthCheckController.java
│  │           │  ├─ bookmark
│  │           │  │  ├─ entity
│  │           │  │  └─ repository
│  │           │  ├─ category
│  │           │  │  ├─ entity
│  │           │  │  │  └─ CategoryEntity.java
│  │           │  │  ├─ enums
│  │           │  │  │  └─ Category.java
│  │           │  │  ├─ repository
│  │           │  │  │  └─ CategoryRepository.java
│  │           │  │  └─ service
│  │           │  │     └─ CategoryService.java
│  │           │  ├─ comment
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ CommentController.java
│  │           │  │  │  └─ CommentControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  └─ request
│  │           │  │  │     ├─ CreateCommentRequest.java
│  │           │  │  │     └─ UpdateCommentRequest.java
│  │           │  │  ├─ entity
│  │           │  │  │  └─ CommentEntity.java
│  │           │  │  ├─ enums
│  │           │  │  │  └─ CommentType.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ CommentRepository.java
│  │           │  │  │  ├─ CommentRepositoryCustom.java
│  │           │  │  │  └─ CommentRepositoryImpl.java
│  │           │  │  └─ service
│  │           │  │     └─ CommentService.java
│  │           │  ├─ file
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ FileController.java
│  │           │  │  │  └─ FileControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ request
│  │           │  │  │  │  └─ PreSignedUrlRequest.java
│  │           │  │  │  └─ response
│  │           │  │  │     └─ PreSignedUrlResponse.java
│  │           │  │  └─ service
│  │           │  │     └─ FileService.java
│  │           │  ├─ home
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ HomeController.java
│  │           │  │  │  └─ HomeControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ request
│  │           │  │  │  └─ response
│  │           │  │  │     └─ GetHomeInfoResponse.java
│  │           │  │  └─ service
│  │           │  │     └─ HomeService.java
│  │           │  ├─ image
│  │           │  │  ├─ entity
│  │           │  │  │  ├─ CommentImageEntity.java
│  │           │  │  │  └─ PopUpStoreImageEntity.java
│  │           │  │  └─ repository
│  │           │  │     ├─ CommentImageRepository.java
│  │           │  │     └─ PopUpStoreImageRepository.java
│  │           │  ├─ interest
│  │           │  │  └─ dto
│  │           │  │     ├─ request
│  │           │  │     └─ response
│  │           │  ├─ like
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ LikeController.java
│  │           │  │  │  └─ LikeControllerDoc.java
│  │           │  │  ├─ entity
│  │           │  │  │  └─ LikeEntity.java
│  │           │  │  ├─ repository
│  │           │  │  │  └─ LikeRepository.java
│  │           │  │  └─ service
│  │           │  │     └─ LikeService.java
│  │           │  ├─ location
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ LocationController.java
│  │           │  │  │  └─ LocationControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ request
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ GetViewBoundPopUpStoreListResponse.java
│  │           │  │  │     └─ SearchPopUpStoreByMapResponse.java
│  │           │  │  ├─ entity
│  │           │  │  │  └─ LocationEntity.java
│  │           │  │  ├─ repository
│  │           │  │  │  └─ LocationRepository.java
│  │           │  │  └─ service
│  │           │  │     └─ LocationService.java
│  │           │  ├─ notice
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ NoticeController.java
│  │           │  │  │  └─ NoticeControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ GetNoticeDetailResponse.java
│  │           │  │  │     └─ GetNoticeListResponse.java
│  │           │  │  ├─ entity
│  │           │  │  │  └─ NoticeEntity.java
│  │           │  │  ├─ repository
│  │           │  │  │  └─ NoticeRepository.java
│  │           │  │  └─ service
│  │           │  │     └─ NoticeService.java
│  │           │  ├─ popup
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ PopUpStoreController.java
│  │           │  │  │  └─ PopUpStoreControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  └─ resonse
│  │           │  │  │     ├─ GetClosedPopUpStoreListResponse.java
│  │           │  │  │     ├─ GetOpenPopUpStoreListResponse.java
│  │           │  │  │     ├─ GetPopUpStoreDetailResponse.java
│  │           │  │  │     └─ GetPopUpStoreDirectionResponse.java
│  │           │  │  ├─ entity
│  │           │  │  │  └─ PopUpStoreEntity.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ PopUpStoreRepository.java
│  │           │  │  │  ├─ PopUpStoreRepositoryCustom.java
│  │           │  │  │  └─ PopUpStoreRepositoryImpl.java
│  │           │  │  └─ service
│  │           │  │     └─ PopUpStoreService.java
│  │           │  ├─ search
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ SearchController.java
│  │           │  │  │  └─ SearchControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  └─ SearchPopUpStoreResponse.java
│  │           │  │  └─ service
│  │           │  │     └─ SearchService.java
│  │           │  ├─ sign_up
│  │           │  │  ├─ controller
│  │           │  │  │  ├─ SignUpController.java
│  │           │  │  │  └─ SignUpControllerDoc.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ request
│  │           │  │  │  │  └─ SignUpRequest.java
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ GetCategoryListResponse.java
│  │           │  │  │     └─ GetGenderResponse.java
│  │           │  │  └─ service
│  │           │  │     └─ SignUpService.java
│  │           │  ├─ token
│  │           │  │  ├─ entity
│  │           │  │  │  ├─ BlackListTokenEntity.java
│  │           │  │  │  └─ RefreshTokenEntity.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ BlackListTokenRepository.java
│  │           │  │  │  └─ RefreshTokenRepository.java
│  │           │  │  └─ service
│  │           │  │     ├─ BlackListTokenService.java
│  │           │  │     └─ RefreshTokenService.java
│  │           │  └─ user
│  │           │     ├─ controller
│  │           │     │  ├─ UserController.java
│  │           │     │  ├─ UserControllerDoc.java
│  │           │     │  ├─ UserProfileController.java
│  │           │     │  └─ UserProfileControllerDoc.java
│  │           │     ├─ dto
│  │           │     │  ├─ request
│  │           │     │  │  ├─ CheckedSurveyListRequest.java
│  │           │     │  │  ├─ UpdateMyInterestCategoryRequest.java
│  │           │     │  │  ├─ UpdateMyProfileRequest.java
│  │           │     │  │  └─ UpdateMyTailoredInfoRequest.java
│  │           │     │  └─ response
│  │           │     │     ├─ GetBlockedUserListResponse.java
│  │           │     │     ├─ GetBookmarkPopUpStoreListResponse.java
│  │           │     │     ├─ GetMyCommentedPopUpStoreListResponse.java
│  │           │     │     ├─ GetMyCommentResponse.java
│  │           │     │     ├─ GetMyPageResponse.java
│  │           │     │     ├─ GetMyRecentViewPopUpStoreListResponse.java
│  │           │     │     ├─ GetProfileResponse.java
│  │           │     │     └─ GetWithDrawlSurveyResponse.java
│  │           │     ├─ entity
│  │           │     │  ├─ BlockedUserEntity.java
│  │           │     │  ├─ BookmarkPopUpStoreEntity.java
│  │           │     │  ├─ RoleEntity.java
│  │           │     │  ├─ UserEntity.java
│  │           │     │  ├─ UserInterestCategoryEntity.java
│  │           │     │  ├─ UserPopUpStoreViewEntity.java
│  │           │     │  ├─ UserRoleEntity.java
│  │           │     │  └─ WithDrawalSurveyEntity.java
│  │           │     ├─ enums
│  │           │     │  ├─ Gender.java
│  │           │     │  ├─ Role.java
│  │           │     │  └─ WithDrawlSurvey.java
│  │           │     ├─ repository
│  │           │     │  ├─ BlockedUserRepository.java
│  │           │     │  ├─ BlockedUserRepositoryCustom.java
│  │           │     │  ├─ BlockedUserRepositoryImpl.java
│  │           │     │  ├─ BookmarkPopUpStoreRepository.java
│  │           │     │  ├─ RoleRepository.java
│  │           │     │  ├─ UserInterestCategoryRepository.java
│  │           │     │  ├─ UserPopUpStoreViewRepository.java
│  │           │     │  ├─ UserRepository.java
│  │           │     │  └─ WithDrawlRepository.java
│  │           │     └─ service
│  │           │        ├─ UserProfileService.java
│  │           │        └─ UserService.java
│  │           ├─ global
│  │           │  ├─ audit
│  │           │  │  ├─ AdminEntityListener.java
│  │           │  │  ├─ BaseAdminEntity.java
│  │           │  │  ├─ BaseEntity.java
│  │           │  │  ├─ BaseTimeAdminEntity.java
│  │           │  │  ├─ BaseTimeEntity.java
│  │           │  │  └─ JpaAuditConfig.java
│  │           │  ├─ config
│  │           │  │  ├─ QuerydslConfig.java
│  │           │  │  ├─ S3Config.java
│  │           │  │  └─ SwaggerConfig.java
│  │           │  ├─ converter
│  │           │  │  ├─ BooleanToYNConverter.java
│  │           │  │  ├─ CategoryConverter.java
│  │           │  │  ├─ CommentTypeConverter.java
│  │           │  │  ├─ EnumToStringConverter.java
│  │           │  │  ├─ GenderConverter.java
│  │           │  │  ├─ StringToCategoryConverter.java
│  │           │  │  └─ WithDrawlSurveyConverter.java
│  │           │  ├─ enums
│  │           │  │  └─ BaseEnum.java
│  │           │  ├─ exception
│  │           │  │  ├─ ApiControllerExceptionAdvice.java
│  │           │  │  ├─ BadRequestException.java
│  │           │  │  ├─ BaseException.java
│  │           │  │  ├─ ConcurrencyException.java
│  │           │  │  ├─ ErrorCode.java
│  │           │  │  ├─ ExceptionResponse.java
│  │           │  │  ├─ NotFoundException.java
│  │           │  │  └─ UnAuthorizedException.java
│  │           │  ├─ jwt
│  │           │  │  ├─ JwtAuthenticationFilter.java
│  │           │  │  ├─ JwtProperties.java
│  │           │  │  └─ JwtService.java
│  │           │  ├─ security
│  │           │  │  ├─ CustomAccessDeniedHandler.java
│  │           │  │  ├─ CustomAuthenticationEntryPoint.java
│  │           │  │  ├─ CustomUserDetailsService.java
│  │           │  │  └─ SecurityConfig.java
│  │           │  └─ utils
│  │           │     ├─ AgeGroupUtils.java
│  │           │     ├─ QueryDslUtils.java
│  │           │     ├─ SecurityUtils.java
│  │           │     └─ TimeUtils.java
│  │           └─ PopPoolApplication.java
│  └─ resources
│     ├─ application-dev.yml
│     ├─ application-local.yml
│     ├─ application.yml
│     ├─ data.sql
│     ├─ static
│     └─ templates
└─ test
   ├─ java
   │  └─ com
   │     └─ application
   │        └─ poppool
   │           └─ PopPoolApplicationTests.java
   └─ resources
      └─ application-test.yml

```

## 👥 협업 관리
- [Notion](https://www.notion.so/POP-POOL-11f3e1b836684b86b4b78dae7d179a23)
</br>

## 📆 프로젝트 기간
### 24.05.15 ~ 24.08.31
</br>

## 📋 프로젝트 관련 문서
- [ERD](https://www.erdcloud.com/d/wkrkmAGhaCbwBpN7X)
</br>
