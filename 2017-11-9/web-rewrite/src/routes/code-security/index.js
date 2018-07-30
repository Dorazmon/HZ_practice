import React from 'react'
import ReactDOM from 'react-dom'
import enquire from 'enquire.js'
import { scrollScreen } from 'rc-scroll-anim'
import { BackTop, Affix, Row, Col } from 'antd' 
import Nav from '../../components/Nav';
import Footer from '../../components/codeFooter';
import './codeSecurity.less';


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
        {/* 导航栏 */}
        <Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} />
        <div className="code_ALL">
          <div className="code_content1">
              <div className="code_content1-text">
                <Row type="flex" align="middle">
                  <Col span={8} style={{textAlign: 'center'}}><img src="/static/code-security/icon2.png" alt="态势图" /></Col>
                  <Col span={16}><h1>代码安全云</h1></Col>
                </Row>
                <p>代码审计云是孝道科技凭借多年来积累的代码审计项目的经验，结合用户的需求，所开发的一套SAAS模式的代码审计系统。该系统采用多租户模式，提供完善的可视化界面，可与第三方代码托管平台(GitHub,OSChina)对接，该系统专业的工具链可与CI系统无缝集成，以最小的代价帮助企业实现代码的质量控制，严控安全源头。</p>
              </div>
              <div className="code_content1-img">
                  <img src="/static/code-security/img1.jpg" />
              </div>
          </div>
          {/*<div className="code_content2">
            <div className="code_content2-title">
              <h1>————&nbsp;&nbsp; 产品理念 &nbsp;&nbsp;————</h1>
                <h2>安全源于代码，研究表明，70%的安全风险源于编码</h2>
                <div className="title-h2"><h2>——让代码安全更简单、更普惠</h2></div>
              </div>
             <div className="code_content2-text">
              <div className="code_content2_1">
                <p>存储、计算、监控、安全，我们提供业界领先水平的服务（测试内容）</p>
                <img src="/static/code-security/tu1.png" />
              </div>
              <div className="code_content2_2">
                <img src="/static/code-security/111.jpg" />
              </div>
            </div>
          </div> */}
          <div className="code_content3">
            <div className="code_content3-title">
              <h1>————&nbsp;&nbsp; 我们的优势 &nbsp;&nbsp;————</h1>
            </div>
            <div className="code_content3-text">
              <div className="code_content3_1">
                <div className="code_content3_1_1">
                  <h1>云端审计和本地审计</h1>
                  <br/>
                  <p>支持代码上传到第三方代码库 （GitHub、OSChina）进行安全检测，也支持下载客户端到本地，方便开发者在本地电脑上进行安全测试。</p>
                </div>
                <div className="code_content3_1_2">
                  <h1>多种开发语言和框架</h1>
                  <br/>
                  <p>开发者可结合自身安全规范定制各种开发语言的规则引擎，同时可在代码安全云上调用新引擎进行测试。</p>
                </div>
              </div>
              <div className="code_content3_2">
                <img src="/static/code-security/tu2.png" />
              </div>
              <div className="code_content3_3">
                <div className="code_content3_3_1">
                <h1>深度学习和大数据结合</h1>
                  <br/>
                  <p>通过代码特征、深度学习模型对海量源代码进行多维度的分析、训练和学习，提升未知安全风险识别和感知能力。</p>
                </div>
                <div className="code_content3_3_2">
                  <h1>让代码安全更简单普惠</h1>
                  <br/>
                  <p>可让所有开发者共同参与安全开发，同时可以拥有简单、普惠的代码安全能力。</p>
                </div>
              </div>
            </div>
          </div>
          <div className="code_content4">
            <div className="code_content4-title">
              <h1>————&nbsp;&nbsp; 反馈项目态势&nbsp;让您了如指掌 &nbsp;&nbsp;————</h1>
            </div>
            <div className="code_content4-text">
              <div className="code_content4_1">
                  <img src="/static/code-security/img2.png"/>
                  <p><h1>以项目为单位的管理</h1></p>
                  <img src="/static/code-security/img4.png" />
                  <p><h1>详尽的描述</h1><h1>让您更好定位问题所在</h1></p>
              </div>
              <div className="code_content4_2">
                <img src="/static/code-security/tu3.png" />
              </div>
              <div className="code_content4_3">
                
                <p><h1>捕获每次提交的审计项目</h1><h1>让您更了解项目质量状况</h1></p>
                <img src="/static/code-security/img3.png"/>
                <p><h1>设定您的执行计划</h1><h1>可自己完全制定规则</h1></p>
                <img src="/static/code-security/img5.png" />
              </div>
            </div>
          </div>
          <div className="code_content5">
            <div className="code_content5-title">
              <h1>————&nbsp;&nbsp; 支持功能 &nbsp;&nbsp;————</h1>
            </div>
            <div className="code_content5-text"> 
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu17.png" />
                  <h1>多种检测引擎</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu20.png" />
                  <h1>团队角色管理</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu14.png" />
                  <h1>Restful</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu12.png" />
                  <h1>双因素</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu18.png" />
                  <h1>SMS & Email</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu13.png" />
                  <h1>CLI支持</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu16.png" />
                  <h1>私有云</h1>
                </div>
                
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu19.png" />
                  <h1>CWE分类</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/code-security/tu15.png" />
                  <h1>GitHub/OSChina</h1>
                </div>
              </div>
          </div>
        </div>
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />
      </div>
    );
  }
}
