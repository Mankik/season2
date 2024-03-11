# vue-project

Vue 프로젝트 템플릿입니다.
1. 이 템플릿 폴더를 `season2/apps/팀이름`으로 복사해주세요.
2. 팀이름의 경우 영문을 권장합니다.
3. 복사한 프로젝트의 `packages.json`에서 `name` 항목을 각자 프로젝트 명을 변경해주세요. (중복되면 오류가 생깁니다)

## 미리 설치된 주요 라이브러리

사용에 도움이 될만한 라이브러리를 미리 설치 해두었습니다.
- [Radix Vue](https://www.radix-vue.com/): 스타일이 지정되어 있지 않고, 접근성을 고려하여 설계된 컴포넌트 라이브러리
- [VueUse](https://vueuse.org/): 드래그&드롭, 클립보드 접근 등 여러가지 유틸들이 들어있는 Vue의 Hooks 라이브러리
- [Pinia](https://pinia.vuejs.org/): 상태 관리 라이브러리
- [Vue Router](https://v3.router.vuejs.org): URL PATH 라우팅 라이브러리

## 프로젝트 셋업

윈도우 유저의 경우
1. [Node.JS](https://nodejs.org/en/download)를 설치합니다.
2. **Powershell을 관리자 권한**으로 열고 [코어팩을 활성화](https://github.com/nodejs/corepack) 합니다.
3. 정상적으로 실행되었다면 닫아주세요. (아래에 있는 명령어는 VS code를 열고 일반 권한의 Powershell을 사용하시면 됩니다) 
```
# 관리자 모드 Powershell
corepack enable
```

맥 유저의 경우
1. [Brew](https://brew.sh/)를 설치합니다.
2. [Node.JS](https://formulae.brew.sh/formula/node)를 설치합니다.
3. [코어팩을 활성화](https://github.com/nodejs/corepack) 합니다.
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
brew install node
corepack enable
```

이제 다음 명령을 실행해 패키지들을 설치합니다.  
단, 프로젝트 폴더의 위치에서 실행해야 합니다.
```sh
yarn install
```

### 다양한 명령어들
- `yarn start`: 개발시 컴파일 및 핫로딩 실행
- `yarn build`: 배포용으로 빌드
- `yarn lint`: 코드 포매팅과 [ESLint](https://eslint.org/) 체크
- `yarn fix`: `yarn lint`에서 에러가 났다면, 자동으로 고치기를 시도합니다
- `yarn check`: 타입을 체크합니다
- `yarn test`: [Vitest](https://vitest.dev/)로 테스트 코드를 실행합니다

### Visual Studio 코드 사용
본인 팀의 프로젝트를 열어주세요!! $\color{#FF0000}\textsf{★중요!!★}$ **`season2`가 아닌 `season2/apps/팀이름`을!!**  
여기서는 템플릿 프로젝트를 열도록 하겠습니다.
![스크린샷 2023-11-14 155748](https://github.com/Mankik/season2/assets/25581533/3a349cda-37d5-473f-9b62-36c70e6f1aad)
![스크린샷 2023-11-14 155843](https://github.com/Mankik/season2/assets/25581533/1ab08305-8900-46fb-8400-c50097c47d42)

린트나 디버거등의 기능을 활성화 하기 위해 신뢰함으로 체크합니다.
![스크린샷 2023-11-14 155955](https://github.com/Mankik/season2/assets/25581533/78b951d5-b750-488f-8b29-ec0e35f39481)

추천하는 확장기능들을 설치합니다.
![스크린샷 2023-11-14 160046](https://github.com/Mankik/season2/assets/25581533/01342d7d-d8b1-4f46-91fe-6b62e1e06a44)
![스크린샷 2023-11-14 160252](https://github.com/Mankik/season2/assets/25581533/e16319e9-00f8-4654-8756-abf5732be01f)

이제 잘 동작하는 모습을 볼 수 있습니다.  
터미널을 열고 명령어도 실행할 수 있습니다.
![스크린샷 2023-11-14 170411](https://github.com/Mankik/season2/assets/25581533/65452c55-c07f-48a4-ae8c-d9d8dcc75e7b)

혹시 `Cannot find module 'vue'. Did you mean to set the 'moduleResolution' option to 'node'`과 같은 문제가 뜬다면 다음 사항들을 체크해주세요.
1. 확장기능에서 [`Vetur`](https://github.com/vuejs/vetur)는 비활성화 해주세요, [`Volar`](https://github.com/vuejs/language-tools)를 사용해야 합니다.
2. Visual Studio 코드의 버전이 [1.77.3](https://stackoverflow.com/questions/75870063/vscode-and-typescript-5-moduleresolution-bundler) 이상인지 확인하십시오.

혹시 성능에 문제가 있다면 [TypeScript and JavaScript](https://github.com/vuejs/language-tools/discussions/471#discussioncomment-1361669)를 워크스페이스에서 비활성화 해볼 수 있습니다.
![image](https://github.com/Mankik/season2/assets/25581533/4cc862cc-d473-45bb-a5de-4aa1bdacdf7f)
