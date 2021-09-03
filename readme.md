# 트리플 클럽 마일리지 적립 

### 기술 스택
- Java11
- Spring Boot
- MySQL
- JPA (Hibernate)
- Kafka

### 실행 방법
```
cd docker
docker-compose up

(....추가)
```

### 데이터베이스 구조

### 마일리지 적립 구조 
(이미지)

**마일리지 적립**  
- 리뷰 이벤트가 발생하면 적절한 작업을 수행한 후 리뷰 데이터와 리뷰 이벤트를 RDB에 저장합니다.
- 저장된 이벤트는 Message Relay 에 의해 주기적으로 마일리지 서버에 적립을 요청합니다.

### API
```
POST /events
주어진 데이터를 이용해 리뷰를 추가/수정/삭제 후 이벤트를 outbox에 저장합니다.
(저장된 이벤트는 주기적으로 MessageRelay에 의해 MessageQueue로 전송되어 마일리지 적립을 처리합니다.  
리스너에서 이벤트를 받으면 리뷰 이벤트를 분석하여 마일리지를 적립/반환 처리합니다.)
```


```
GET /mileages?userId={USER_ID}&page={PAGE}
ID가 주어진 유저의 포인트 합산(sum)과 주어진 적립 내역을 페이징하여 제공합니다. 

응답 샘플
{
  "sum": 0,
  "logs": {
    "content": [
      {
        "createdDate": "2021-09-03T01:20:30.318794",
        "mileageId": "bf3534c1-5bfc-44aa-8112-268468ff7c05",
        "amount": -2,
        "userId": "14012214-226d-4667-8410-c5556959e139",
        "reviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "originReviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "reason": "Review is deleted."
      },
      {
        "createdDate": "2021-09-03T01:20:12.263933",
        "mileageId": "4a288cba-9cd5-4d1b-9703-46031d7f44e6",
        "amount": -1,
        "userId": "14012214-226d-4667-8410-c5556959e139",
        "reviewId": "79fbf9ab-6802-4c97-945d-c728b3e8ba85",
        "originReviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "reason": "Photos are removed."
      },
      {
        "createdDate": "2021-09-03T01:08:10.393533",
        "mileageId": "413984a2-208b-4878-aff2-a1a607e8ba24",
        "amount": 1,
        "userId": "14012214-226d-4667-8410-c5556959e139",
        "reviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "originReviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "reason": "First review of the place"
      },
      {
        "createdDate": "2021-09-03T01:08:10.392942",
        "mileageId": "450499c5-b1b5-4561-b2f4-92d93f0a9b6b",
        "amount": 1,
        "userId": "14012214-226d-4667-8410-c5556959e139",
        "reviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "originReviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "reason": "Content is newly included."
      },
      {
        "createdDate": "2021-09-03T01:08:10.388598",
        "mileageId": "37830801-f74e-4fe1-a8e2-f8e96402e25d",
        "amount": 1,
        "userId": "14012214-226d-4667-8410-c5556959e139",
        "reviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "originReviewId": "24a06458-dc5f-4878-9381-ebb7b2667772",
        "reason": "Photos are newly included."
      }
    ],
    "pageable": {
        ... (생략)
```


#### TODO
- [x] 리뷰 요청시 validation
- [ ] 응답 포맷 통일
- [x] 단위테스트 추가
- [ ] 통합테스트 추가
- [x] restTemplate retry