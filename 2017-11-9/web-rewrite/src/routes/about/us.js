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
        <Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} />
        <div className="about_ALL">
          <div className="about_banner"><h1>ABOUT US/关于我们</h1><h2>让安全更简单&nbsp;&nbsp;&nbsp;更普惠&nbsp;&nbsp;&nbsp;让世界互联更安全</h2><img src="/static/about/border.png"/></div>
          
          <div className="about_block1">{/* 通栏背景 */}
            <div className="about_content1">{/* 内部整体块 */}
              <div className="about_content_title">{/* 标题 */}
                <h2>公司简介</h2>
                <h3>COMPANY INTRODUCTION</h3>
              </div>
              <div className="about_content1_text">{/* 内容 */}
                
                <div className="about_content1_text_content">
                  <p>杭州孝道科技有限公司（简称：孝道科技）座落在美丽的西子湖畔，位于中国（杭州）智慧信息产业园区，公司创立于2014年，由业内知名安全公司、海归博士等人员共同创办。公司一直致力于信息安全技术的研究、实践和积累，专注为用户提供专业的安全云服务、安全检测服务和安全咨询服务。</p>
                  <br />
                  <p>孝道科技目前已服务于全国百余家大型制造企业，包括传化集团、物产集团、兴合集团、海尔集团、航天科工集团、义乌小商品城、海康威视、中策集团及通策集团等大型企业；同时已覆盖了全省的海尔快捷通、甬易支付、商盟商务、连连支付、网易宝、贝付科技、传化金服、航天电子、义乌小商品城支付、网盛生意宝等十多家支付公司，另外还涵盖了各大互联网消费金融和互联网电商企业，包括鑫合汇、爱学贷、点点搜财、永达互联网金融、小拇指、什么值得买、东海商品、泰一指尚等知名企业。</p>
              </div>
              </div>
            </div>
          </div>

          <div className="about_block2">{/* 通栏背景 */}
            <div className="about_content2">{/* 内部整体块 */}
              <div className="about_content_title">{/* 标题 */}
                <h2>企业使命</h2>
                <h3>ENTERPRISE MISSION</h3>
              </div>
              <div className="about_content2_text">{/* 内容 */}
                
                <div className="about_content2_text_content"> <p>让安全更简单、更普惠，让世界互联更安全</p> </div>
              </div>
            </div>
          </div>

          <div className="about_block3">{/* 通栏背景 */}
            <div className="about_content3">{/* 内部整体块 */}
              <div className="about_content_title">{/* 标题 */}
                <h2>企业愿景</h2>
                <h3>ENTERPRISE VISION</h3>
              </div>
              <div className="about_content3_text">{/* 内容 */}
                
                <div className="about_content3_text_content"> <p>在五年内成为国内受尊敬的技术型安全公司</p> </div>
              </div>
            </div>
          </div>

          <div className="about_block4">{/* 通栏背景 */}
            <div className="about_content4">{/* 内部整体块 */}
              <div className="about_content_title">{/* 标题 */}
                <h2>企业价值观</h2>
                <h3>ENTERPRISE VALUE</h3>
              </div>
              <div className="about_content4_text">{/* 内容 */}
                <div className="about_content4_text_content"> <p>学习、创新、坚持、梦想</p><p>诚实、守信、合作、共赢</p> </div>
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
