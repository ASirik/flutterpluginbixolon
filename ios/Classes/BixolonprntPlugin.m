#import "BixolonprntPlugin.h"
#if __has_include(<bixolonprnt/bixolonprnt-Swift.h>)
#import <bixolonprnt/bixolonprnt-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "bixolonprnt-Swift.h"
#endif

@implementation BixolonprntPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftBixolonprntPlugin registerWithRegistrar:registrar];
}
@end
