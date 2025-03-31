import AppRouter from "./components/AppRouter";
import {ConfigProvider, Layout} from "antd";
import "./App.css"
import {QueryClient, QueryClientProvider} from "react-query";
import Navbar from "./components/Navbar";
export const App = () => {
    const queryClient = new QueryClient({
        defaultOptions: {
            queries: {
                refetchOnWindowFocus: false
            }
        }
    });

    return (
        <ConfigProvider>
            <QueryClientProvider client={queryClient}>
                <Layout>
                    <Navbar/>
                    <Layout.Content>
                        <div className="page">
                            <AppRouter/>
                        </div>
                    </Layout.Content>
                </Layout>
            </QueryClientProvider>
        </ConfigProvider>

    );
};
