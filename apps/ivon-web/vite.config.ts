import type { ConfigEnv } from "vite";
import { VueConfig } from "vite-config-custom";

// https://vitejs.dev/config/
export default (viteConfigEnv: ConfigEnv) => {
  return VueConfig(viteConfigEnv);
};
