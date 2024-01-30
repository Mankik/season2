const { defineConfig } = require('eslint-define-config');

module.exports = defineConfig({
  // https://github.com/jsx-eslint/eslint-plugin-react#configuration
  plugins: [
    "jsx-a11y",
    "react-refresh"
  ],
  extends: [
    "custom",
    "plugin:react/recommended",
    "plugin:react/jsx-runtime",
    "plugin:jsx-a11y/recommended",
    "plugin:react-hooks/recommended"
  ],
  rules: {
    "react-refresh/only-export-components": [
      "warn",
      { allowConstantExport: true },
    ],
  },
  settings: {
    react: {
      version: "detect"
    }
  },
  env: {
    browser: true,
    es2020: true
  }
});
