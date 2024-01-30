import type { ConfigEnv } from "vite";
import { ReactConfig } from "vite-config-custom";

// == Vite Config =============================================================
// https://vitejs.dev/config/
export default (viteConfigEnv: ConfigEnv) => {
  return ReactConfig(viteConfigEnv);
};
