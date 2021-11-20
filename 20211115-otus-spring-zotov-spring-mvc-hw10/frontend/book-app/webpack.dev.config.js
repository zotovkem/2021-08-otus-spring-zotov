const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path');

module.exports = {
    mode: 'development',
    entry: {
        app: path.join(__dirname, 'src', 'index.tsx')
    },
    devtool: 'inline-source-map',
    output: {
        path: path.resolve(__dirname, "build"),
        filename: "bundle.js",
    },
    target: 'web',
    devServer: {
        compress: true,
        port: 3000,
        host: 'localhost',
        open: true,

    },

    resolve: {
        // Добавить разрешения '.ts' и '.tsx' к обрабатываемым
        extensions: [".webpack.js", ".web.js", ".ts", ".tsx", ".js"]
    },
    module: {
        rules: [
            {
                test: /\.(ts|js)x?$/,
                exclude: /node_modules/,
                use: [
                    {
                        loader: 'babel-loader',
                    },
                ],
            }
        ]
    },
    externals: {
        "react": "React",
        "react-dom": "ReactDOM"
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname,  'public/index.html'),
        }),
    ]
}
