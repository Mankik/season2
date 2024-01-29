import { createGlobalStyle } from "styled-components";

const GlobalStyles = createGlobalStyle`

  html, body, div, p {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  :root {
    --color__primary: #E75151;
  }
`;

export default GlobalStyles;
