import AppRouter from './shared/router/AppRouter';
import {ConfigProvider, Layout as AntdLayout} from 'antd';
import './App.css';
import {QueryClient, QueryClientProvider} from 'react-query';
import Navbar from './shared/ui/Navbar';
import {Provider} from "mobx-react";
import {stores} from "./stores/stores";

export const App = () => {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        refetchOnWindowFocus: false,
        retry: 0
      }
    }
  });

  return (
    <Provider {...stores}>
      <ConfigProvider>
        <QueryClientProvider client={queryClient}>
          <div className="page">
            <AppRouter/>
          </div>
        </QueryClientProvider>
      </ConfigProvider>
    </Provider>
  );
};
