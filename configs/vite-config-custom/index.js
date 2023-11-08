import { resolve } from "path";
import { fileURLToPath, URL } from "node:url";
import { initConfigBuilder, ViteEnv, PluginBuilder } from "vite-config-builder";
import { mergeConfig } from "vite";

import dts from "vite-plugin-dts";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";

// == Main Configs ============================================================
export function NodeConfig(viteConfigEnv, extendConfigs = {}) {
  return buildConfig(viteConfigEnv, extendConfigs, NodeBuilder);
}

export function VueConfig(viteConfigEnv, extendConfigs = {}) {
  return buildConfig(viteConfigEnv, extendConfigs, VueBuilder);
}

function buildConfig(viteConfigEnv, extendConfigs, configBuilder) {
  return mergeConfig({
    ...configBuilder(viteConfigEnv).build()
  }, extendConfigs);
}

// == Main Configs ============================================================
const rootDir = resolve(process.cwd(), "src/");

function NodeBuilder(viteConfigEnv) {
  const { configs, plugins } = initCommonBuilder(viteConfigEnv);

  if (ViteEnv.isProd()) {
    plugins.add(
      dts({
        entryRoot: rootDir,
        include: ["src"]
      })
    );
  }

  configs.add({
    build: {
      // https://vitejs.dev/guide/build.html#library-mode
      lib: {
        entry: resolve(process.cwd(), "src/index.ts"),
        formats: ["es", "cjs"],
        fileName: format => (format === "es" ? "index.mjs" : "index.cjs")
      },
      target: [ "es2020" ]
    },
    plugins: plugins.build()
  });
  return configs;
}

function VueBuilder(viteConfigEnv) {
  const { configs, plugins } = initCommonBuilder(viteConfigEnv);

  plugins.add(vue());
  plugins.add(vueJsx());
  configs.add({
    plugins: plugins.build()
  });
  return configs;
}

function initCommonBuilder(viteConfigEnv) {
  const configs = initConfigBuilder(viteConfigEnv);

  configs.add({
    resolve: {
      alias: {
        "@": fileURLToPath(new URL(rootDir, import.meta.url))
      }
    }
  })

  if (ViteEnv.isDev()) {
    configs.add({
      build: {
        sourcemap: true,
        minify: false,
        rollupOptions: {
          treeshake: false
        }
      }
    });
  }

  if (ViteEnv.isProd()) {
    configs.add({
      build: {
        sourcemap: false,
        minify: "terser"
      }
    });
  }

  if (ViteEnv.isTest()) {
    configs.add({
      test: {
        includeSource: ["src/**/*.ts", "src/**/*.tsx"],
        globals: true
      }
    });
  } else {
    configs.add({
      define: {
        "import.meta.vitest": "undefined"
      }
    });
  }

  const plugins = new PluginBuilder([
  ]);

  return {
    configs,
    plugins
  };
}
