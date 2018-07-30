import React from 'react'
import ReactDOM from 'react-dom'
import enquire from 'enquire.js'
import { scrollScreen } from 'rc-scroll-anim'
import { BackTop, Affix, Row, Col } from 'antd' 
import Nav from '../../components/Nav'
import Footer from '../../components/Footer'

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
        <Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} />
        <div className="web" style={{marginTop: ' 0px' }}>
          <div className="banner" id="sdl_banner"style={{background: 'url(/static/services/sdl-banner.jpg) no-repeat' }}>
            <div className="sdl"><h1>SDL安全开发周期</h1></div>
          </div>
          <div className="sdl_content">
            <h1>我们的解决方案</h1>
            {/* <img src="/static/services/sdl1.png"/> */}
            <div className="sdl_text">
              <div className="sdl_text1">
                <ul className="sdl_text1_1">
                  <li>安全需求分析</li>
                  <li>攻击面分析</li>
                  <li>安全设计规范</li>
                  <li>威胁建模</li>
                </ul>
                <ul className="sdl_text1_2">
                  <li>黑白盒、社工</li>
                  <li>CHECKLIST</li>
                  <li>配置安全</li>
                </ul>
              </div>
              <div className="sdl_text2">
                <ul className="sdl_text2_1">
                  <li>安全意识培训</li>
                  <li>安全编码培训</li>
                </ul>
                <ul className="sdl_text2_2">
                  <li>各类语言安全编码规范</li>
                  <li>安全自测系统</li>
                </ul>
                <ul className="sdl_text2_3">
                  <li>漏洞预警、发现和响应</li>
                  <li>应急预案和服务</li>
                  <li>BUG追踪</li>
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
