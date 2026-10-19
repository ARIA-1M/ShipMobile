import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'
import tailwindcss from "@tailwindcss/vite";
import {federation} from "@module-federation/vite";

// https://vite.dev/config/
export default defineConfig({
    base: 'http://lesson-vs.sfera.local/',
    plugins: [
        react(),
        tailwindcss(),
        federation({
            name: 'lesson-vs',
            filename:'remoteEntry.js',
            manifest: true,
            exposes: {
                './App': './src/main/App.tsx'
            },
            shared: {
                react: {
                    singleton: true,
                    requiredVersion: '^19.1.1',
                    strictVersion: false,
                },
                'react-dom': {
                    singleton: true,
                    requiredVersion: '^19.1.1',
                    strictVersion: false,
                },
            },
        }),
        tailwindcss(),
    ],
    build: {target: 'esnext', minify: false},
    server: { headers: { 'Access-Control-Allow-Origin': '*' } },
    preview: {
        host: '0.0.0.0',
        port: 5001,
        strictPort: true,
        headers: {'Access-Control-Allow-Origin': '*'}
    },
})
