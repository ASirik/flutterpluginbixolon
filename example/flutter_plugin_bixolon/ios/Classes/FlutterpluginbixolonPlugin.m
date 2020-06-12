#import "FlutterpluginbixolonPlugin.h"
#if __has_include(<flutterpluginbixolon/flutterpluginbixolon-Swift.h>)
#import <flutterpluginbixolon/flutterpluginbixolon-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutterpluginbixolon-Swift.h"
#endif

@implementation FlutterpluginbixolonPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterpluginbixolonPlugin registerWithRegistrar:registrar];
}
@end
