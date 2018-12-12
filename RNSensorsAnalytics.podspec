require 'json'
version = JSON.parse(File.read('package.json'))["version"]

Pod::Spec.new do |s|
  s.name             = 'RNSensorsAnalytics'
  s.version          = version
  s.summary          = 'RNSensorsAnalytics'

  s.description      = <<-DESC
TODO: Add long description of the pod here.
                       DESC

  s.homepage         = 'https://github.com/shimohq/react-native-sa'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'lisong' => 'lisong@shimo.im' }
  s.source           = { :git => 'https://github.com/shimohq/react-native-sa.git', :tag => s.version.to_s }

  s.ios.deployment_target = '8.0'
  
  s.source_files = 'ios/**/*.{h,m,mm}'
  
  s.dependency 'React'
  s.dependency 'SensorsAnalyticsSDK'

end