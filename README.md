# Simple Fintech

## 프로젝트 개요

회원관리 기능 & 간단한 계좌 관리 서비스

## 프로젝트 기능 정의

### 회원

- 회원 가입 기능
  - 서비스를 이용하기 위해서 모든 사용자는 회원가입을 해야 한다.
  - 회원가입시 실명, 이름, 이메일, 비밀번호을 입력받으며, 이메일은 unique 해야한다.

- 로그인 기능
  - 사용자는 로그인을 할 수 있다. 로그인시 회원가입때 사용한 아이디와 패스워드가 일치해야한다.

### 계좌 (로그인 필수)

- 계좌 개설 기능
  - 로그인한 사용자는 계좌를 개설을 할 수 있다.
  - 계좌 개설 시 계좌 번호, 계좌 비밀번호를 입력 받으며, 계좌 번호는 unique 해야하고 계좌 비밀번호는 4자리의 숫자여야 한다.

- 계좌 검색 기능
  - 계좌 번호를 통해 검색할 수 있다.

- 계좌 관리 기능
  - 계좌는 입금, 출금, 송금 기능을 제공한다.
    - 출금과 송금은 본인의 계좌에 한하여 가능하다.

- 계좌 이체 내역 조회
  - 본인의 계좌에 한하여 이체 내역을 조회할 수 있다.
  - 이쳬는 입금, 출금, 전체로 필터링이 가능하다.

## ERD

![ERD](doc/assets/Screenshot%202023-07-15%20at%205.19.51%20PM.png)

## Trouble Shooting

[go to the trouble shooting section](doc/TROUBLE_SHOOTING.md)


### Tech Stack

<div align=center>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img height="30" src="https://img.shields.io/badge/GitHub-black?style=flat-square&logo=GitHub&logoColor=white"/>
</div>
