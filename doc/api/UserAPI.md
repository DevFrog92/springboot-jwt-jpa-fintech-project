# API documentation

Fintech 서비스의 API 명세서

> 작성자: DevFro92 <br/>
> 최초 작성일: 2023.07.25 <br/>
> 최근 수정일: 2023.07.05 - [수정 사항](./FixDetail.md)


## 회원가입

```text
- POST /api/join
```

- 메서드: `POST`
- 헤더 토큰 포함여부(Y/N): `N`
- `Content-Type`: `application/json`
- 파라미터

    |필드|타입|설명|필수(Y/N)|정책|
    |-|-|-|-|-|
    |username|String|사용자 이름|Y|4~20자 이내의 영문과 숫자를 혼합한 문자열(TBD)|
    |email|String|사용자 이메일|Y|이메일 형식에 맞는 문자열|
    |password|String|사용자 이름|Y|4~20자 이내의 문자열(TBD)|

<br/>

- 요청 및 응답 구조
  - 요청

    ```json
    {
        "username": "user123",
        "email": "user@gmail.com",
        "password": "userPassword"
    }
    ```

  - 응답

    - 회원가입에 성공한 경우

      `STATUS CODE: 201`

        ``` json
        {
            "status": 201,
            "message": "회원가입에 성공했습니다.",
            "body": {
                "username": "user123",
                "email": "user@gamil.com"
            }
        }
        ```

    - 회원가입에 실패한 경우

      `STATUS CODE: 400`

      - 이미 가입된 사용자

        ```json
          {
            "status": 400,
            "message": "중복된 사용자가 있습니다.",
            "body": null
        }
        ```

      - 필드 에러

        ```json
          {
            "status": 400,
            "message": "유효성 검사에 실패했습니다.",
            "body": {
                username: "username validation message",
                email: "email validation message",
                password: "password validation message"
            }
        }
