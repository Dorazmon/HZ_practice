import React, { PropTypes } from 'react';
import { Button, Icon } from 'antd';
import QueueAnim from 'rc-queue-anim';
import TweenOne, { TweenOneGroup } from 'rc-tween-one';
import BannerAnim, { Element } from 'rc-banner-anim';
import 'rc-banner-anim/assets/index.css';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';

const BgElement = Element.BgElement;
class Banner extends React.Component {
  render() {
    const props = { ...this.props };
    delete props.isMode;
    const childrenData = [
      {
        button: '进入我们',
        src: '/code-security',
      },
      {
        button: '进入我们',
        src: 'application-security/web-security',
      },
    ];
    const childrenToRender = childrenData.map((item, i) => {
      const button = item.button;
      const src = item.src;
      const ii = i;
      return (<Element
        key={i}
        prefixCls="banner-user-elem"
      >
        <BgElement
          className={`bg bg${i}`}
          key="bg"
        >
        </BgElement>
        
        <QueueAnim
          type={['bottom', 'top']} delay={200}
          className={`${props.className}-title`}
          key="text"
          id={`${props.id}-wrapperBlock${i}`}
        >
        <a href={src}>
          <Button
            type="ghost"
            key="button"
            id={`${props.id}-buttonBlock${i}`}
          >
            {button}
          </Button>
        </a>
        }
        </QueueAnim>
      </Element>);
    });
    return (
      <OverPack
        {...props}
      >
        <TweenOneGroup
          key="banner"
          enter={{ opacity: 0, type: 'from' }}
          leave={{ opacity: 0 }}
          component=""
        >
          <div className={`${props.className}-wrapper`}>
            <BannerAnim
              key="banner"
            >
              {childrenToRender}
            </BannerAnim>
          </div>
        </TweenOneGroup>
        <TweenOne
          animation={{ y: '-=20', yoyo: true, repeat: -1, duration: 1000 }}
          className={`${props.className}-icon`}
          style={{ bottom: 40 }}
          key="icon"
        >
          <Icon type="down" />
        </TweenOne>
      </OverPack>
    );
  }
}

Banner.defaultProps = {
  className: 'banner1',
};

export default Banner;

