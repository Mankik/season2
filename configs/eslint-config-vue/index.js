const { defineConfig } = require("eslint-define-config");

module.exports = defineConfig({
  extends: [
    "eslint:recommended",
    "plugin:vue/vue3-essential",
    "plugin:vuejs-accessibility/recommended",
    "@vue/eslint-config-typescript/recommended",
    "@vue/eslint-config-prettier"
  ],
  plugins: [
    "vuejs-accessibility"
  ],
  parserOptions: {
    ecmaVersion: "latest",
    sourceType: "module",
    EXPERIMENTAL_useProjectService: true
  },
  rules: {
    "prettier/prettier": ["error", { "endOfLine": "auto" }]
  },
  env: {
    browser: true,
    es2020: true
  }
});
