# 마일리지 적립

### 리뷰 이벤트 발생 API
- `POST /events`  

### 사용되는 API
- 포인트 내역 추가 : `POST /points`
- 특정 유저의 포인트 조회 : `POST /users/{user_id}/points`

#### TODO
- [x] 리뷰 요청시 validation
- [ ] 응답 포맷 통일
- [ ] 테스트 추가
- [x] restTemplate retry