import React from 'react';
import ReactDOM from 'react-dom';
import enquire from 'enquire.js';
import { scrollScreen } from 'rc-scroll-anim';
import { BackTop, Affix, Row, Col } from 'antd';
import Nav from '../../components/Nav';
import Footer from '../../components/Footer';
import './about.less';

export default class User extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isMode: false
    };
  }

  componentDidMount() {
    // 适配手机屏幕;
    this.enquireScreen((isMode) => {
      this.setState({ isMode });
    });
  }

  enquireScreen = (cb) => {
    /* eslint-disable no-unused-expressions */
    enquire.register('only screen and (min-width: 320px) and (max-width: 767px)', {
      match: () => {
        cb && cb(true);
      },
      unmatch: () => {
        cb && cb();
      },
    });
    /* eslint-enable no-unused-expressions */
  }

  render() {
    return (
      <div className="templates-wrapper">
        <Affix><Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} /></Affix>,
        <div className="bussiness_ALL">
          <div className="bussiness_banner"><h1>业务介绍</h1><p>孝道科技专注为用户提供代码安全、云安全、移动安全、大数据安全及物联网</p><p>安全等安全产品和安全服务，立志成为业界最具有影响力的综合信息安全解决方案提供商</p></div>
          
          <div className="bussiness_block1">{/* 通栏背景 */}
            <div className="bussiness_content1">{/* 内部整体块 */}
              <div className="bussiness_content_title">{/* 标题 */}
                <h1>代码安全</h1>
              </div>
              <div className="bussiness_content2_text">{/* 内容 */}
                <ul className="bussiness_list">
                  <li>代码安全云</li>
                </ul>
              </div>
            </div>
          </div>
          <div className="bussiness_block1">{/* 通栏背景 */}
            <div className="bussiness_content1">{/* 内部整体块 */}
              <div className="bussiness_content_title">{/* 标题 */}
                <h1>应用安全</h1>
              </div>
              <div className="bussiness_content2_text">{/* 内容 */}
                <ul className="bussiness_list1">
                  <li>网站安全：<span>网站安全检查、网站安全加固</span></li>
                  <li>移动安全：<span>移动安全组件、移动安全加固</span></li>
                </ul>
              </div>
            </div>
          </div>

          <div className="bussiness_block1">{/* 通栏背景 */}
            <div className="bussiness_content1">{/* 内部整体块 */}
              <div className="bussiness_content_title">{/* 标题 */}
                <h1>安全检测服务</h1>
              </div>
              <div className="bussiness_content2_text">{/* 内容 */}
                <ul className="bussiness_list">
                  <li>安全风险评估</li><li>Web渗透测试</li><li>APP渗透测试</li>
                  <li>数据隐私检测</li><li>应急护航服务</li><li>木马病毒检测</li>
                  <li>控件保护软件安全检测</li><li>安全情报服务</li><li></li>
                </ul>
              </div>
            </div>
          </div>

          <div className="bussiness_block1">{/* 通栏背景 */}
            <div className="bussiness_content1">{/* 内部整体块 */}
              <div className="bussiness_content_title">{/* 标题 */}
                <h1>安全咨询服务</h1>
              </div>
              <div className="bussiness_content2_text">{/* 内容 */}
                <ul className="bussiness_list">
                  <li>等级保护</li><li>非金融机构支付安全</li><li>PCI-DSS数据安全标准</li><li>银行信息科技风险管理</li>
                  <li>信息安全管理体系</li><li>业务连续性及敏感信息保护</li>
                  
                </ul>
              </div>
            </div>
          </div>
        </div>

        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />,
      </div>
    );
  }
}
