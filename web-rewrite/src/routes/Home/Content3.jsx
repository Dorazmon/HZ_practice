import React from 'react';
import QueueAnim from 'rc-queue-anim';
import TweenOne from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';

class Content extends React.Component {

  static defaultProps = {
    className: 'content2',
  };

  getDelay = e => e % 3 * 100 + Math.floor(e / 3) * 100 + 300;

  render() {
    const props = { ...this.props };
    delete props.isMode;
    const oneAnim = { y: '+=30', opacity: 0, type: 'from', ease: 'easeOutQuad' };
    const blockArray = [
      { icon: '/static/icon/icon3.png', title: '代码安全',src: '#/about', content: '代码安全云' },
      { icon: '/static/icon/icon1.png', title: '网站安全', src: '#/about', content: '综合安全检查' },
      { icon: '/static/icon/icon4.png', title: '移动安全', src: '#/about', content: 'APP加固#APP安全组件' },
      { icon: '/static/icon/icon5.png', title: 'SDL', src: '#/about', content: '威胁建模#代码审计' },
      { icon: '/static/icon/icon6.png', title: '安全检测', src: '#/about', content: '安全风险评估#WEB渗透测试 #数据隐私检测#APP渗透测试' },
      { icon: '/static/icon/icon2.png', title: '安全咨询', src: '#/about', content: '非金融机构支付安全#信息科技风险管理#等级保护#信息安全管理体系#PCI DSS数据安全标准' },
    ];
    const children = blockArray.map((item, i) => {
      const id = `block${i}`;
      const delay = this.getDelay(i);
      const liAnim = { opacity: 0, type: 'from', ease: 'easeOutQuad', delay };
      const childrenAnim = { ...liAnim, x: '+=10', delay: delay + 100,};
      const content = item.content.split(/#/).filter(contentItem => contentItem)
        .map((contentItem, ii) => {
          const contentid = `contentlist-${ii}`;
          return ( 
          <li className="contentlist" id={contentid}>
            <a href="#">
              {contentItem}
            </a>
            <span>&nbsp;|&nbsp;</span>
          </li>);
        });
      return (<TweenOne
        component="li"
        animation={liAnim}
        key={i}
        id={`${props.id}-${id}`}
      >
        <TweenOne
          animation={{ x: '-=10', opacity: 0, type: 'from', ease: 'easeOutQuad' }}
          className="img"
          key="img"
        >
          <img src={item.icon} />
        </TweenOne>
        <div className="text">
          <TweenOne key="h1" animation={childrenAnim} component="h1">
            {item.title}
          </TweenOne>
          <TweenOne key="p" animation={{ ...childrenAnim, delay: delay + 200 }} component="ul" className="contentlist-ul">
            {content}
          </TweenOne>
        </div>
      </TweenOne>);
    });
    return (
      <div {...props} className={`content-template-wrapper ${props.className}-wrapper`}>
        <OverPack
          className={`content-template ${props.className}`}
          location={props.id}
        >
          <TweenOne
            key="h1"
            animation={oneAnim}
            component="h1"
            id={`${props.id}-title`}
            reverseDelay={100}
          >
             孝道科技云提供专业的服务
          </TweenOne>
          <TweenOne
            key="p"
            animation={{ ...oneAnim, delay: 100 }}
            component="p"
            id={`${props.id}-titleContent`}
          >
            professional services provided by XiaoDao Technology Cloud
          </TweenOne>
          <QueueAnim
            key="ul"
            type="bottom"
            className={`${props.className}-contentWrapper`}
            id={`${props.id}-contentWrapper`}
          >
            <ul key="ul">
              {children}
            </ul>
          </QueueAnim>
        </OverPack>
      </div>
    );
  }
}


export default Content;
