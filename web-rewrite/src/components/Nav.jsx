import React from 'react';
import PropTypes from 'prop-types';
import TweenOne from 'rc-tween-one';
import { Menu, Icon } from 'antd';
import { Router, Route, Link } from 'react-router';
import './nav.less';

const Item = Menu.Item;
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

class Header extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      phoneOpen: false,
      current: 'app',
    };
  }
  componentWillMount() {
    const href = this.getKey();
    this.setState({ current: href });
  }
  phoneClick = () => {
    this.setState({
      phoneOpen: !this.state.phoneOpen,
    });
  }
  getKey() {
    let href = window.location.href;
    href = href.split('/');
    href = href[href.length - 1];
    
    if (href === '') {
      href = 'index';
    }
    console.log(href);
    return href;
  }
  handleClick=(e)=> {
    console.log('click ', e);
    this.setState({
      current: e.key,
    });
  }

  render() {
    const props = { ...this.props };
    const isMode = props.isMode;
    delete props.isMode;
    const navData = { menu1: '首页', menu2: '评估检测类服务', menu3: '资讯类服务', menu4: '安全云服务', menu5: '关于我们' }; ; 
    const navChildren = Object.keys(navData)
      .map((key, i) => (<Item key={i}>{navData[key]}</Item>));
    return (
    <TweenOne
      component="header"
      animation={{ opacity: 0, type: 'from' }}
      {...props}
    >
      <TweenOne
        className={`${this.props.className}-logo`}
        animation={{ x: -30, type: 'from', ease: 'easeOutQuad' }}
        id={`${this.props.id}-logo`}
      >
        {/* <Link to="/" ><img width="100%" src="http://www.tcsec.com.cn/wp-content/uploads/2017/07/logo-2.png" /></Link> */}
        <Link to="/"><img src="/static/img/logo.png" /></Link>
      </TweenOne>
      {isMode ? (<div
        className={`${this.props.className}-phone-nav${this.state.phoneOpen ? ' open' : ''}`}
        id={`${this.props.id}-menu`}
      >
        <div
          className={`${this.props.className}-phone-nav-bar`}
          onClick={() => {
            this.phoneClick();
          }}
        >
          <em />
          <em />
          <em />
        </div>
        {/* 手机显示 */}
        <div
          className={`${this.props.className}-phone-nav-text`}
        >
        <Menu
          onClick={this.handleClick}
          selectedKeys={[this.state.current]}
          mode="inline"
          theme="dark"
          id={`${this.props.id}-menu`}
        >
        <Menu.Item key="index">
        <a href="/" style={{ color: 'white' }}><Icon type="mail" />首页</a>
        </Menu.Item>
        <Menu.Item key="app">
          <Icon type="appstore" />代码安全云
        </Menu.Item>
        <SubMenu title={<span><Icon type="setting" />安全云服务</span>}>
          <SubMenu title={"评估检测类"} >
            <Menu.Item key="web-penetration"><Link to="/services/web-penetration">web渗透测试</Link></Menu.Item>
            <Menu.Item key="app-penetration"><Link to="/services/app-penetration">APP渗透测试</Link></Menu.Item>
            <Menu.Item key="safety-assessment"><Link to="/services/safety-assessment">安全风险评估</Link></Menu.Item>
            <Menu.Item key="code-audit"><Link to="/services/code-audit">源代码审计</Link></Menu.Item>
          </SubMenu>
          <SubMenu title={"咨询类"} >
            <Menu.Item key="sdl"><Link to="/services/sdl">开发安全</Link></Menu.Item>
            <Menu.Item key="cloud-security"><Link to="/services/cloud-security">云安全</Link></Menu.Item>
          </SubMenu>
        </SubMenu>
        <SubMenu title={<span><Icon type="api" />关于我们</span>} >
          <Menu.Item key="about"><Link to="/about">我们是谁</Link></Menu.Item>
          <Menu.Item key="recruitment"><Link to="/recruitment">招聘信息</Link></Menu.Item>
      </SubMenu>
      </Menu>
        </div>
      </div>) : (<TweenOne
        className={`${this.props.className}-nav`}
        animation={{ x: 30, type: 'from', ease: 'easeOutQuad' }}
      >
      <Menu
        onClick={this.handleClick}
        selectedKeys={[this.state.current]}
        mode="horizontal"
        id={`${this.props.id}-menu`}
      >
       <Menu.Item key="code-security">
         <Link to="code-security">代码安全云</Link>
       </Menu.Item>
       <SubMenu title={<span>应用安全云</span>} >
          <Menu.Item key="#1"><Link to="#1">网站应用</Link></Menu.Item>
          <Menu.Item key="#2"><Link to="#2">移动应用</Link></Menu.Item>
       </SubMenu>  
        <SubMenu title={ <span>安全服务</span> } > 
          <SubMenu title={"安全检测"} >
            <Menu.Item key="safety-assessment"><Link to="/services/safety-assessment">安全风险评估</Link></Menu.Item>
            <Menu.Item key="web-penetration"><Link to="/services/web-penetration">WEB渗透测试</Link></Menu.Item>
            <Menu.Item key="app-penetration"><Link to="/services/app-penetration">APP渗透测试</Link></Menu.Item>
            <Menu.Item key="code-audit"><Link to="/services/code-audit">数据隐私检测</Link></Menu.Item>
          </SubMenu>
          <SubMenu title={"安全咨询"} >
            <Menu.Item key="consultation-1" ><Link to="/consultation/consultation1" className="consultation">非金融机构支付安全</Link></Menu.Item>
            <Menu.Item key="consultation-2"><Link to="/consultation/consultation2" className="consultation">PCI DSS数据安全标准</Link></Menu.Item>
            <Menu.Item key="consultation-3"><Link to="/consultation/consultation3" className="consultation">信息安全管理体系</Link></Menu.Item>
            <Menu.Item key="consultation-4"><Link to="/consultation/consultation4" className="consultation">等级保护</Link></Menu.Item>
            <Menu.Item key="consultation-5"><Link to="/consultation/consultation5" className="consultation">信息科技风险管理</Link></Menu.Item>
          </SubMenu>
        </SubMenu>
        {/* <SubMenu title={ <span>关于我们</span> } >
          <SubMenu title={"应用安全"} >
            <Menu.Item key="web-2"><Link to="/services/web-penetration">DDOS防护</Link></Menu.Item>
            <Menu.Item key="app-penetration"><Link to="/services/app-penetration">云WAF</Link></Menu.Item>
            <Menu.Item key="safety-assessment"><Link to="/services/safety-assessment">主机安全</Link></Menu.Item>
            <Menu.Item key="safety-1"><Link to="/services/safety-assessment">态势感知</Link></Menu.Item>
            <Menu.Item key="safety-2"><Link to="/services/safety-assessment">实名认证</Link></Menu.Item>
          </SubMenu>
          <Menu.Item key="sdl"><Link to="/services/sdl">移动安全</Link></Menu.Item>
          <Menu.Item key="cloud-security"><Link to="/services/cloud-security">数据安全</Link></Menu.Item>
          <Menu.Item key="1"><Link to="/services/cloud-security">内容安全</Link></Menu.Item>
          <Menu.Item key="2"><Link to="/services/cloud-security">业务安全</Link></Menu.Item>
        </SubMenu> */}
        
        <SubMenu title={<span>关于我们</span>} >
          <Menu.Item key="about"><Link to="/about">我们是谁</Link></Menu.Item>
          <Menu.Item key="about1"><Link to="/about">我们做什么</Link></Menu.Item>
          <Menu.Item key="about2"><Link to="/about">我们的用户</Link></Menu.Item>
          <Menu.Item key="about3"><Link to="/about">合作生态</Link></Menu.Item>
          <Menu.Item key="recruitment"><Link to="/recruitment">招聘信息</Link></Menu.Item>
        </SubMenu>
    </Menu>
      {/* <Menu.Item key="index">
      <a href="/" style={{ color: 'white' }}><Icon type="mail" />首页</a>
      </Menu.Item>
      <Menu.Item key="app">
        <Icon type="appstore" />代码安全云
      </Menu.Item>
      <SubMenu title={<span><Icon type="setting" />安全云服务</span>}>
        <SubMenu title={"评估检测类"} >
          <Menu.Item key="web-penetration"><Link to="/services/web-penetration">web渗透测试</Link></Menu.Item>
          <Menu.Item key="app-penetration"><Link to="/services/app-penetration">APP渗透测试</Link></Menu.Item>
          <Menu.Item key="safety-assessment"><Link to="/services/safety-assessment">安全风险评估</Link></Menu.Item>
          <Menu.Item key="code-audit"><Link to="/services/code-audit">源代码审计</Link></Menu.Item>
        </SubMenu>
        <SubMenu title={"咨询类"} >
          <Menu.Item key="sdl"><Link to="/services/sdl">开发安全</Link></Menu.Item>
          <Menu.Item key="cloud-security"><Link to="/services/cloud-security">云安全</Link></Menu.Item>
        </SubMenu>
      </SubMenu>
      <SubMenu title={<span><Icon type="api" />关于我们</span>} >
        <Menu.Item key="about"><Link to="/about">我们是谁</Link></Menu.Item>
        <Menu.Item key="recruitment"><Link to="/recruitment">招聘信息</Link></Menu.Item>
    </SubMenu>
    </Menu> */}
      </TweenOne>)}
    </TweenOne>);
  }
}

Header.propTypes = {
  className: PropTypes.string,
  dataSource: PropTypes.object,
  id: PropTypes.string,
};

Header.defaultProps = {
  className: 'header0',
};

export default Header;
